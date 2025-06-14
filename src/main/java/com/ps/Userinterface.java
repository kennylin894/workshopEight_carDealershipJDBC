package com.ps;

import java.time.Year;
import java.util.ArrayList;
import java.util.Scanner;

public class Userinterface {

    private VehicleDao vehicleDao;
    private Scanner scanner = new Scanner(System.in);

    public Userinterface(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    public void display() {
        System.out.println("Welcome to the dealership program.");
        System.out.println("==================================");

        int mainMenuCommand = 0;
        do {
            System.out.println("\nWhat would you like to do?");
            System.out.println("[1] Get by price");
            System.out.println("[2] Get by make/model");
            System.out.println("[3] Get by year");
            System.out.println("[4] Get by color");
            System.out.println("[5] Get by mileage");
            System.out.println("[6] Get by type");
            System.out.println("[7] Get all");
            System.out.println("[8] Add vehicle");
            System.out.println("[9] Remove vehicle");
            System.out.println("[10] Sell/Lease a vehicle");
            System.out.println("[0] Exit");

            System.out.print("Command: ");
            try {
                mainMenuCommand = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number 0-10.");
                continue;
            }

            switch (mainMenuCommand) {
                case 1:
                    processGetByPriceRequest();
                    break;
                case 2:
                    processGetByMakeModelRequest();
                    break;
                case 3:
                    processGetByYearRequest();
                    break;
                case 4:
                    processGetByColorRequest();
                    break;
                case 5:
                    processGetByMileageRequest();
                    break;
                case 6:
                    processGetByVehicleTypeRequest();
                    break;
                case 7:
                    processGetAllVehiclesRequest();
                    break;
                case 8:
                    processAddVehicleRequest();
                    break;
                case 9:
                    processRemoveVehicleRequest();
                    break;
                case 10:
                    processContractVehicle();
                    break;
                case 0:
                    System.out.println("Thank you for visiting! Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 0-10.");
            }
        } while (mainMenuCommand != 0);
    }

    private void processGetByPriceRequest() {
        System.out.println("\n=== Search by Price Range ===");
        System.out.print("Enter minimum price: $");
        double min = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Enter maximum price: $");
        double max = Double.parseDouble(scanner.nextLine().trim());
        if (min < 0 || max < min) {
            System.out.println("Invalid price range. Please try again.");
            return;
        }
        System.out.println("Searching for vehicles between $" + min + " and $" + max + "...");
        ArrayList<Vehicle> vehicles = vehicleDao.getVehiclesByPriceRange(min, max);
        printingVehiclesOut(vehicles);
    }

    private void processGetByMakeModelRequest() {
        System.out.println("\n=== Search by Make/Model ===");
        System.out.print("Enter make: ");
        String make = scanner.nextLine().trim();
        System.out.print("Enter model: ");
        String model = scanner.nextLine().trim();
        if (make.isEmpty() || model.isEmpty()) {
            System.out.println("Make and model cannot be empty.");
            return;
        }
        System.out.println("Searching for " + make + " " + model + " vehicles...");
        ArrayList<Vehicle> vehicles = vehicleDao.getVehiclesByMakeModel(make, model);
        printingVehiclesOut(vehicles);
    }

    private void processGetByYearRequest() {
        System.out.println("\n=== Search by Year Range ===");
        System.out.print("Enter starting year: ");
        int min = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter ending year: ");
        int max = Integer.parseInt(scanner.nextLine().trim());
        int currentYear = Year.now().getValue();
        if (min < 1990 || max > currentYear + 1 || min > max) {
            System.out.println("Invalid year range. Please try again.");
            return;
        }
        System.out.println("Searching for vehicles from " + min + " to " + max + "...");
        ArrayList<Vehicle> vehicles = vehicleDao.getVehiclesByYearRange(min, max);
        printingVehiclesOut(vehicles);
    }

    private void processGetByColorRequest() {
        System.out.println("\n=== Search by Color ===");
        System.out.print("Enter color: ");
        String color = scanner.nextLine().trim();
        if (color.isEmpty()) {
            System.out.println("Color cannot be empty.");
            return;
        }
        System.out.println("Searching for " + color + " vehicles...");
        ArrayList<Vehicle> vehicles = vehicleDao.getVehiclesByColor(color);
        printingVehiclesOut(vehicles);
    }

    private void processGetByMileageRequest() {
        System.out.println("\n=== Search by Mileage Range ===");
        System.out.print("Enter minimum mileage: ");
        int min = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter maximum mileage: ");
        int max = Integer.parseInt(scanner.nextLine().trim());
        if (min < 0 || max < min) {
            System.out.println("Invalid mileage range. Please try again.");
            return;
        }
        System.out.println("Searching for vehicles with " + min + " to " + max + " miles...");
        ArrayList<Vehicle> vehicles = vehicleDao.getVehiclesByMileageRange(min, max);
        printingVehiclesOut(vehicles);
    }

    private void processGetByVehicleTypeRequest() {
        System.out.println("\n=== Search by Vehicle Type ===");
        System.out.print("Enter vehicle type (Sedan, SUV, Truck, etc.): ");
        String type = scanner.nextLine().trim();
        if (type.isEmpty()) {
            System.out.println("Vehicle type cannot be empty.");
            return;
        }
        System.out.println("Searching for " + type + " vehicles...");
        ArrayList<Vehicle> vehicles = vehicleDao.getVehiclesByType(type);
        printingVehiclesOut(vehicles);
    }

    private void processGetAllVehiclesRequest() {
        System.out.println("\n=== All Vehicles ===");
        ArrayList<Vehicle> vehicles = vehicleDao.getAllVehicles();
        printingVehiclesOut(vehicles);
    }

    private void processAddVehicleRequest() {
        System.out.println("\n=== Add New Vehicle ===");
        System.out.print("Enter VIN: ");
        String vin = scanner.nextLine().trim().toUpperCase();
        System.out.print("Enter year: ");
        int year = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter make: ");
        String make = scanner.nextLine().trim();
        System.out.print("Enter model: ");
        String model = scanner.nextLine().trim();
        System.out.print("Enter vehicle type: ");
        String type = scanner.nextLine().trim();
        System.out.print("Enter color: ");
        String color = scanner.nextLine().trim();
        System.out.print("Enter odometer reading: ");
        int odometer = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter price: $");
        double price = Double.parseDouble(scanner.nextLine().trim());
        if (vin.isEmpty() || make.isEmpty() || model.isEmpty() || type.isEmpty() || color.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }
        Vehicle newVehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);
        vehicleDao.addVehicle(newVehicle);
        System.out.println("✓ Vehicle added successfully!");
    }

    private void processRemoveVehicleRequest() {
        System.out.println("\n=== Remove Vehicle ===");
        System.out.print("Enter VIN of vehicle to remove: ");
        String vin = scanner.nextLine().trim().toUpperCase();
        if (vin.isEmpty()) {
            System.out.println("VIN cannot be empty.");
            return;
        }
        System.out.print("Are you sure you want to remove vehicle " + vin + "?");
        System.out.println("[1] Yes");
        System.out.println("[2] No");
        int confirm = Integer.parseInt(scanner.nextLine().trim());
        if (confirm == 1) {
            vehicleDao.removeVehicle(vin);
            System.out.println("✓ Vehicle removal attempted.");
        } else if (confirm == 2) {
            System.out.println("Vehicle removal cancelled.");
        } else {
            System.out.println("Please enter a valid number.");
        }
    }

    private void processContractVehicle() {
        System.out.println("\n=== Contract Vehicle ===");
    }

//    private Vehicle findVehicleByVin(String vin) {
//        ArrayList<Vehicle> allVehicles = vehicleDao.getAllVehicles();
//        for (Vehicle vehicle : allVehicles) {
//            if (vehicle.getVin().equals(vin)) {
//                return vehicle;
//            }
//        }
//        return null;
//    }

    public void printingVehiclesOut(ArrayList<Vehicle> list) {
        String line = "+-------------------+------+------------+-----------+-------------+--------+------------+-------------+";
        System.out.println(line);
        System.out.printf("| %-17s | %-4s | %-10s | %-9s | %-11s | %-6s | %-10s | %-11s |\n", "VIN", "Year", "Make", "Model", "Type", "Color", "Odometer", "Price");
        System.out.println(line);

        if (list.isEmpty()) {
            System.out.println("|                                    No vehicles found                                     |");
        } else {
            for (Vehicle vehicle : list) {
                System.out.printf("| %-17s | %-4d | %-10s | %-9s | %-11s | %-6s | %-10d | $%-10.2f |\n", vehicle.getVin(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(), vehicle.getVehicleType(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice());
            }
        }
        System.out.println(line);
        System.out.println();
    }
}