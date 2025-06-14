package com.ps;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleDao {
    // Datasource
    private DataSource dataSource;

    public VehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<Vehicle> getAllVehicles() {
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        String query = "SELECT * FROM vehicles;";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            if (resultSet.next()) {
                do {
                    Vehicle vehicle = vehicleParser(resultSet);
                    vehicles.add(vehicle);
                } while (resultSet.next());
            } else {
                System.out.println("No vehicles found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public ArrayList<Vehicle> getVehiclesByPriceRange(double minPrice, double maxPrice) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        String query = "SELECT * FROM vehicles WHERE price BETWEEN ? AND ?;";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setDouble(1, minPrice);
            preparedStatement.setDouble(2, maxPrice);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    do {
                        Vehicle vehicle = vehicleParser(resultSet);
                        vehicles.add(vehicle);
                    } while (resultSet.next());
                } else {
                    System.out.println("No vehicles found in price range");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public ArrayList<Vehicle> getVehiclesByMakeModel(String make, String model) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        String query = "SELECT * FROM vehicles WHERE make = ? AND model = ?;";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, make);
            preparedStatement.setString(2, model);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    do {
                        Vehicle vehicle = vehicleParser(resultSet);
                        vehicles.add(vehicle);
                    } while (resultSet.next());
                } else {
                    System.out.println("No vehicles found for make/model");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public ArrayList<Vehicle> getVehiclesByYearRange(int minYear, int maxYear) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        String query = "SELECT * FROM vehicles WHERE year BETWEEN ? AND ?;";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, minYear);
            preparedStatement.setInt(2, maxYear);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    do {
                        Vehicle vehicle = vehicleParser(resultSet);
                        vehicles.add(vehicle);
                    } while (resultSet.next());
                } else {
                    System.out.println("No vehicles found in year range");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public ArrayList<Vehicle> getVehiclesByColor(String color) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        String query = "SELECT * FROM vehicles WHERE color = ?;";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, color);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    do {
                        Vehicle vehicle = vehicleParser(resultSet);
                        vehicles.add(vehicle);
                    } while (resultSet.next());
                } else {
                    System.out.println("No vehicles found for color");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public ArrayList<Vehicle> getVehiclesByMileageRange(int minMileage, int maxMileage) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        String query = "SELECT * FROM vehicles WHERE mileage BETWEEN ? AND ?;";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, minMileage);
            preparedStatement.setInt(2, maxMileage);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    do {
                        Vehicle vehicle = vehicleParser(resultSet);
                        vehicles.add(vehicle);
                    } while (resultSet.next());
                } else {
                    System.out.println("No vehicles found in mileage range");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public ArrayList<Vehicle> getVehiclesByType(String vehicleType) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        String query = "SELECT * FROM vehicles WHERE type = ?;";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, vehicleType);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    do {
                        Vehicle vehicle = vehicleParser(resultSet);
                        vehicles.add(vehicle);
                    } while (resultSet.next());
                } else {
                    System.out.println("No vehicles found for type");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        String query = "INSERT INTO vehicles(VIN, year, make, model, type, color, mileage, price, SOLD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, vehicle.getVin());
            preparedStatement.setInt(2, vehicle.getYear());
            preparedStatement.setString(3, vehicle.getMake());
            preparedStatement.setString(4, vehicle.getModel());
            preparedStatement.setString(5, vehicle.getVehicleType());
            preparedStatement.setString(6, vehicle.getColor());
            preparedStatement.setInt(7, vehicle.getOdometer());
            preparedStatement.setDouble(8, vehicle.getPrice());
            preparedStatement.setBoolean(9, vehicle.isSold());

            int rows = preparedStatement.executeUpdate();

            if (rows == 1) {
                System.out.println("Vehicle successfully added");
            } else {
                System.out.println("Vehicle addition failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeVehicle(String vin) {
        String query = "DELETE FROM vehicles WHERE VIN = ?;";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, vin);

            int rows = preparedStatement.executeUpdate();

            if (rows == 1) {
                System.out.println("Vehicle successfully removed");
            } else {
                System.out.println("Vehicle removal failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Vehicle vehicleParser(ResultSet resultSet) throws SQLException {
        String vin = resultSet.getString("VIN");
        int year = resultSet.getInt("year");
        String make = resultSet.getString("make");
        String model = resultSet.getString("model");
        String vehicleType = resultSet.getString("type");
        String color = resultSet.getString("color");
        int odometer = resultSet.getInt("mileage");
        double price = resultSet.getDouble("price");
        boolean sold = resultSet.getBoolean("sold");

        Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
        vehicle.setSold(sold);

        return vehicle;
    }
}