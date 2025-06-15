package com.ps;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaseDao {
    private DataSource dataSource;
    private VehicleDao vehicleDao;

    public LeaseDao(DataSource dataSource, VehicleDao vehicleDao){
        this.dataSource = dataSource;
        this.vehicleDao = vehicleDao;
    }

    public List<LeaseContract> getAllLeaseContracts(){
        List<LeaseContract> contracts = new ArrayList<>();
        String query = "SELECT * FROM lease_contracts;";
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ){
            if(resultSet.next()){
                do{
                    LeaseContract contract = leaseContractParser(resultSet);
                    contracts.add(contract);
                } while(resultSet.next());
            } else {
                System.out.println("No lease contracts found");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return contracts;
    }

    public LeaseContract getLeaseContractById(int id){
        String query = "SELECT * FROM lease_contracts WHERE id = ?;";
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ){
            preparedStatement.setInt(1, id);
            try(
                    ResultSet resultSet = preparedStatement.executeQuery();
            ){
                if(resultSet.next()){
                    return leaseContractParser(resultSet);
                } else {
                    System.out.println("No lease contract found with ID: " + id);
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<LeaseContract> getLeaseContractsByVin(String vin){
        List<LeaseContract> contracts = new ArrayList<>();
        String query = "SELECT * FROM lease_contracts WHERE VIN = ?;";
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ){
            preparedStatement.setString(1, vin);
            try(
                    ResultSet resultSet = preparedStatement.executeQuery();
            ){
                if(resultSet.next()){
                    do{
                        LeaseContract contract = leaseContractParser(resultSet);
                        contracts.add(contract);
                    } while(resultSet.next());
                } else {
                    System.out.println("No lease contracts found for VIN: " + vin);
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return contracts;
    }

    public void addLeaseContract(LeaseContract contract){
        String query = "INSERT INTO lease_contracts(VIN, lease_date, lease_price, customer_name, customer_email, lease_months) VALUES (?, ?, ?, ?, ?, ?);";
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ){
            preparedStatement.setString(1, contract.getVehicleChoosen().getVin());
            preparedStatement.setString(2, contract.getDate());
            preparedStatement.setDouble(3, contract.getTotalPrice());
            preparedStatement.setString(4, contract.getCustomerName());
            preparedStatement.setString(5, contract.getCustomerEmail());
            preparedStatement.setInt(6, 36);
            int rows = preparedStatement.executeUpdate();
            if(rows == 1){
                System.out.println("Lease contract successfully added");
            } else {
                System.out.println("Lease contract addition failed");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteLeaseContract(int id){
        String query = "DELETE FROM lease_contracts WHERE id = ?;";
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ){
            preparedStatement.setInt(1, id);
            int rows = preparedStatement.executeUpdate();
            if(rows == 1){
                System.out.println("Lease contract successfully deleted");
            } else {
                System.out.println("Lease contract deletion failed");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private LeaseContract leaseContractParser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String vin = resultSet.getString("VIN");
        String leaseDate = resultSet.getString("lease_date");
        String customerName = resultSet.getString("customer_name");
        String customerEmail = resultSet.getString("customer_email");
        Vehicle vehicle = findVehicleByVin(vin);
        if (vehicle == null) {
            vehicle = new Vehicle(vin, 0, "SOLD", "VEHICLE", "N/A", "N/A", 0, 0.0);
        }
        return new LeaseContract(id, leaseDate, customerName, customerEmail, vehicle);
    }

    private Vehicle findVehicleByVin(String vin) {
        List<Vehicle> allVehicles = vehicleDao.getAllVehicles();
        for (Vehicle vehicle : allVehicles) {
            if (vehicle.getVin().equals(vin)) {
                return vehicle;
            }
        }
        return null;
    }
}