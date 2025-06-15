package com.ps;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesDao {
    private DataSource dataSource;
    private VehicleDao vehicleDao;

    public SalesDao(DataSource dataSource, VehicleDao vehicleDao){
        this.dataSource = dataSource;
        this.vehicleDao = vehicleDao;
    }

    public List<SalesContract> getAllSalesContracts(){
        List<SalesContract> contracts = new ArrayList<>();
        String query = "SELECT * FROM sales_contracts;";
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ){
            if(resultSet.next()){
                do{
                    SalesContract contract = salesContractParser(resultSet);
                    contracts.add(contract);
                } while(resultSet.next());
            } else {
                System.out.println("No sales contracts found");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return contracts;
    }

    public SalesContract getSalesContractById(int id){
        String query = "SELECT * FROM sales_contracts WHERE id = ?;";
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ){
            preparedStatement.setInt(1, id);
            try(
                    ResultSet resultSet = preparedStatement.executeQuery();
            ){
                if(resultSet.next()){
                    return salesContractParser(resultSet);
                } else {
                    System.out.println("No sales contract found with ID: " + id);
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<SalesContract> getSalesContractsByVin(String vin){
        List<SalesContract> contracts = new ArrayList<>();
        String query = "SELECT * FROM sales_contracts WHERE VIN = ?;";
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
                        SalesContract contract = salesContractParser(resultSet);
                        contracts.add(contract);
                    } while(resultSet.next());
                } else {
                    System.out.println("No sales contracts found for VIN: " + vin);
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return contracts;
    }

    public void addSalesContract(SalesContract contract){
        String query = "INSERT INTO sales_contracts(VIN, sale_date, sale_price, customer_name, customer_email) VALUES (?, ?, ?, ?, ?);";
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ){
            preparedStatement.setString(1, contract.getVehicleChoosen().getVin());
            preparedStatement.setString(2, contract.getDate());
            preparedStatement.setDouble(3, contract.getTotalPrice());
            preparedStatement.setString(4, contract.getCustomerName());
            preparedStatement.setString(5, contract.getCustomerEmail());
            int rows = preparedStatement.executeUpdate();
            if(rows == 1){
                System.out.println("Sales contract successfully added");
            } else {
                System.out.println("Sales contract addition failed");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteSalesContract(int id){
        String query = "DELETE FROM sales_contracts WHERE id = ?;";
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ){
            preparedStatement.setInt(1, id);
            int rows = preparedStatement.executeUpdate();
            if(rows == 1){
                System.out.println("Sales contract successfully deleted");
            } else {
                System.out.println("Sales contract deletion failed");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private SalesContract salesContractParser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String vin = resultSet.getString("VIN");
        String saleDate = resultSet.getString("sale_date");
        double salePrice = resultSet.getDouble("sale_price");
        String customerName = resultSet.getString("customer_name");
        String customerEmail = resultSet.getString("customer_email");
        Vehicle vehicle = findVehicleByVin(vin);
        if (vehicle == null) {
            throw new SQLException("Vehicle not found for VIN: " + vin);
        }
        boolean isFinanced = salePrice > vehicle.getPrice() * 1.1;
        return new SalesContract(saleDate, customerName, customerEmail, vehicle, isFinanced);
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