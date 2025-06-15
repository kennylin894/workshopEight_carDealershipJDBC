package com.ps;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Userinterface {
    private VehicleDao vehicleDao;
    private SalesDao salesDao;
    private LeaseDao leaseDao;
    private Scanner scanner = new Scanner(System.in);

    public Userinterface(VehicleDao vehicleDao, SalesDao salesDao, LeaseDao leaseDao) {
        this.vehicleDao = vehicleDao;
        this.salesDao = salesDao;
        this.leaseDao = leaseDao;
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
            System.out.println("[11] Delete/View Contracts (ADMIN ONLY)");
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
                case 11:
                    processDeleteContracts();
                    break;
                case 0:
                    System.out.println("Thank you for visiting! Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 0-10.");
            }
        } while (mainMenuCommand != 0);
    }

    private void processDeleteContracts() {
        System.out.println("Please enter the password.");
        String pass = scanner.nextLine();
        if(pass.equals("yearup")) {
            System.out.println("\n=== Delete Contracts ===");
            System.out.println("Do you want to:");
            System.out.println("[1] Delete Sale Contract");
            System.out.println("[2] Delete Lease Contract");
            System.out.println("[3] View Contracts");
            try {
                int saleLease = Integer.parseInt(scanner.nextLine().trim());
                if(saleLease == 1) {
                    System.out.println("\n--- Delete Sales Contract ---");
                    System.out.print("Enter the Sales Contract ID to delete: ");
                    int contractId = Integer.parseInt(scanner.nextLine().trim());
                    salesDao.deleteSalesContract(contractId);
                }
                else if(saleLease == 2) {
                    System.out.println("\n--- Delete Lease Contract ---");
                    System.out.print("Enter the Lease Contract ID to delete: ");
                    int contractId = Integer.parseInt(scanner.nextLine().trim());
                    leaseDao.deleteLeaseContract(contractId);
                } else if (saleLease == 3) {
                    System.out.println("\n--- View Contracts ---");
                    System.out.println("[1] View Sales Contracts");
                    System.out.println("[2] View Lease Contracts");
                    int choice = Integer.parseInt(scanner.nextLine().trim());
                    if(choice == 1) {
                        System.out.println("\n--- Sale Contracts ---");
                        System.out.println("[1] View all Sales");
                        System.out.println("[2] Find by vin");
                        System.out.println("[3] Find by contract id");
                        int viewChoice = Integer.parseInt(scanner.nextLine().trim());
                        if(viewChoice == 1) {
                            ArrayList<SalesContract> contracts = salesDao.getAllSalesContracts();
                            System.out.println("\n=== ALL SALES CONTRACTS ===");
                            System.out.printf("%-5s %-20s %-17s %-12s %-12s %-10s%n",
                                    "ID", "Customer", "VIN", "Date", "Total", "Financed");
                            System.out.println("--------------------------------------------------------------------------------");
                            for (SalesContract contract : contracts) {
                                System.out.printf("%-5d %-20s %-17s %-12s $%-11.2f %-10s%n",
                                        contract.getId(),
                                        contract.getCustomerName(),
                                        contract.getVehicleChoosen().getVin(),
                                        contract.getDate(),
                                        contract.getTotalPrice(),
                                        contract.isFinanced() ? "YES" : "NO");
                            }
                        }
                        else if(viewChoice == 2) {
                            System.out.print("Enter VIN: ");
                            String vin = scanner.nextLine().trim();
                            List<SalesContract> contracts = salesDao.getSalesContractsByVin(vin);
                            if (contracts.isEmpty()) {
                                System.out.println("No sales contracts found for VIN: " + vin);
                            } else {
                                System.out.println("\n=== SALES CONTRACTS FOR VIN: " + vin + " ===");
                                System.out.printf("%-5s %-20s %-12s %-12s %-10s%n",
                                        "ID", "Customer", "Date", "Total", "Financed");
                                System.out.println("-------------------------------------------------------------");
                                for (SalesContract contract : contracts) {
                                    System.out.printf("%-5d %-20s %-12s $%-11.2f %-10s%n",
                                            contract.getId(),
                                            contract.getCustomerName(),
                                            contract.getDate(),
                                            contract.getTotalPrice(),
                                            contract.isFinanced() ? "YES" : "NO");
                                }
                            }
                        }
                        else if(viewChoice == 3) {
                            System.out.print("Enter Contract ID: ");
                            int contractId = Integer.parseInt(scanner.nextLine().trim());
                            SalesContract contract = salesDao.getSalesContractById(contractId);
                            if (contract == null) {
                                System.out.println("No sales contract found with ID: " + contractId);
                            } else {
                                System.out.println("\n=== SALES CONTRACT DETAILS ===");
                                System.out.println("Contract ID: " + contract.getId());
                                System.out.println("Customer: " + contract.getCustomerName());
                                System.out.println("Email: " + contract.getCustomerEmail());
                                System.out.println("Date: " + contract.getDate());
                                System.out.println("Vehicle: " + contract.getVehicleChoosen().getYear() + " " +
                                        contract.getVehicleChoosen().getMake() + " " +
                                        contract.getVehicleChoosen().getModel());
                                System.out.println("VIN: " + contract.getVehicleChoosen().getVin());
                                System.out.println("Vehicle Price: $" + String.format("%.2f", contract.getVehicleChoosen().getPrice()));
                                System.out.println("Sales Tax: $" + String.format("%.2f", contract.getSalesTax() * contract.getVehicleChoosen().getPrice()));
                                System.out.println("Recording Fee: $" + String.format("%.2f", contract.getRecordingFee()));
                                System.out.println("Processing Fee: $" + String.format("%.2f", contract.getProcessingFee()));
                                System.out.println("Total Price: $" + String.format("%.2f", contract.getTotalPrice()));
                                System.out.println("Financed: " + (contract.isFinanced() ? "YES" : "NO"));
                                if (contract.isFinanced()) {
                                    System.out.println("Monthly Payment: $" + String.format("%.2f", contract.getMonthlyPayment()));
                                }
                            }
                        }
                        else {
                            System.out.println("Error: Please enter a valid number.");
                        }
                    }
                    else if(choice == 2) {
                        System.out.println("\n--- Lease Contracts ---");
                        System.out.println("[1] View all Leases");
                        System.out.println("[2] Find by vin");
                        System.out.println("[3] Find by contract id");
                        int viewChoice = Integer.parseInt(scanner.nextLine().trim());
                        if(viewChoice == 1) {
                            List<LeaseContract> contracts = leaseDao.getAllLeaseContracts();
                            if (contracts.isEmpty()) {
                                System.out.println("No lease contracts found.");
                            } else {
                                System.out.println("\n=== ALL LEASE CONTRACTS ===");
                                System.out.printf("%-5s %-20s %-17s %-12s %-12s %-10s%n",
                                        "ID", "Customer", "VIN", "Date", "Total", "Monthly");
                                System.out.println("--------------------------------------------------------------------------------");
                                for (LeaseContract contract : contracts) {
                                    System.out.printf("%-5d %-20s %-17s %-12s $%-11.2f $%-9.2f%n",
                                            contract.getId(),
                                            contract.getCustomerName(),
                                            contract.getVehicleChoosen().getVin(),
                                            contract.getDate(),
                                            contract.getTotalPrice(),
                                            contract.getMonthlyPayment());
                                }
                            }
                        }
                        else if(viewChoice == 2) {
                            System.out.print("Enter VIN: ");
                            String vin = scanner.nextLine().trim();
                            List<LeaseContract> contracts = leaseDao.getLeaseContractsByVin(vin);
                            if (contracts.isEmpty()) {
                                System.out.println("No lease contracts found for VIN: " + vin);
                            } else {
                                System.out.println("\n=== LEASE CONTRACTS FOR VIN: " + vin + " ===");
                                System.out.printf("%-5s %-20s %-12s %-12s %-10s%n",
                                        "ID", "Customer", "Date", "Total", "Monthly");
                                System.out.println("-------------------------------------------------------------");
                                for (LeaseContract contract : contracts) {
                                    System.out.printf("%-5d %-20s %-12s $%-11.2f $%-9.2f%n",
                                            contract.getId(),
                                            contract.getCustomerName(),
                                            contract.getDate(),
                                            contract.getTotalPrice(),
                                            contract.getMonthlyPayment());
                                }
                            }
                        }
                        else if(viewChoice == 3) {
                            System.out.print("Enter Contract ID: ");
                            int contractId = Integer.parseInt(scanner.nextLine().trim());
                            LeaseContract contract = leaseDao.getLeaseContractById(contractId);
                            if (contract == null) {
                                System.out.println("No lease contract found with ID: " + contractId);
                            } else {
                                System.out.println("\n=== LEASE CONTRACT DETAILS ===");
                                System.out.println("Contract ID: " + contract.getId());
                                System.out.println("Customer: " + contract.getCustomerName());
                                System.out.println("Email: " + contract.getCustomerEmail());
                                System.out.println("Date: " + contract.getDate());
                                System.out.println("Vehicle: " + contract.getVehicleChoosen().getYear() + " " +
                                        contract.getVehicleChoosen().getMake() + " " +
                                        contract.getVehicleChoosen().getModel());
                                System.out.println("VIN: " + contract.getVehicleChoosen().getVin());
                                System.out.println("Vehicle Price: $" + String.format("%.2f", contract.getVehicleChoosen().getPrice()));
                                System.out.println("Expected Ending Value: $" + String.format("%.2f", contract.getExpectedEndingValue()));
                                System.out.println("Lease Fee: $" + String.format("%.2f", contract.getLeaseFee()));
                                System.out.println("Lease Months: 36");
                                System.out.println("Total Lease Cost: $" + String.format("%.2f", contract.getTotalPrice()));
                                System.out.println("Monthly Payment: $" + String.format("%.2f", contract.getMonthlyPayment()));
                            }
                        }
                        else {
                            System.out.println("Error: Please enter a valid number.");
                        }
                    }
                    else {
                        System.out.println("Error: Please enter a valid number.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
        else {
            System.out.println("Incorrect Password.");
        }
    }

    private void processContractVehicle() {
        System.out.println("\n=== Contract Vehicle ===");
        System.out.println("Do you want to:");
        System.out.println("[1] Sell");
        System.out.println("[2] Lease");
        int sellLease = Integer.parseInt(scanner.nextLine().trim());
        boolean isFinanced = false;
        if(sellLease != 1 && sellLease != 2) {
            System.out.println("Error, bad input. Try again.");
            return;
        }
        if(sellLease == 1) {
            System.out.println("Do you want to finance?");
            System.out.println("[1] Yes");
            System.out.println("[2] No");
            int financeChoice = Integer.parseInt(scanner.nextLine().trim());
            if(financeChoice == 1) {
                isFinanced = true;
            }
        }
        System.out.print("What is the VIN of the vehicle? ");
        String vin = scanner.nextLine().trim().toUpperCase();
        Vehicle vehicle = findVehicleByVin(vin);
        if(vehicle == null) {
            System.out.println("❌ Vehicle with VIN " + vin + " not found.");
            return;
        }
        if(sellLease == 2) {
            if(Year.now().getValue() - vehicle.getYear() > 3) {
                System.out.println("❌ You cannot lease this vehicle that is more than 3 years old.");
                return;
            }
        }
        System.out.print("What is your full name? ");
        String name = scanner.nextLine().trim();
        System.out.print("What is your email? ");
        String email = scanner.nextLine().trim();
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = now.format(dateTimeFormatter);
        if(sellLease == 1) {
            SalesContract salesContract = new SalesContract(formattedDate, name, email, vehicle, isFinanced);
            System.out.println("\n=== Sales Contract Summary ===");
            System.out.println("Vehicle: " + vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel());
            System.out.println("VIN: " + vehicle.getVin());
            System.out.println("Customer: " + name + " (" + email + ")");
            System.out.println("Total Price: $" + String.format("%.2f", salesContract.getTotalPrice()));
            if(isFinanced) {
                System.out.println("Monthly Payment: $" + String.format("%.2f", salesContract.getMonthlyPayment()));
            } else {
                System.out.println("Payment: Full payment required");
            }
            System.out.print("Confirm this sale? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if(confirm.equals("y") || confirm.equals("yes")) {
                salesDao.addSalesContract(salesContract);
                vehicleDao.removeVehicle(vin);
                System.out.println("✓ Sale completed successfully!");
            } else {
                System.out.println("Sale cancelled.");
            }
        } else if(sellLease == 2) {
            LeaseContract leaseContract = new LeaseContract(formattedDate, name, email, vehicle);
            System.out.println("\n=== Lease Contract Summary ===");
            System.out.println("Vehicle: " + vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel());
            System.out.println("VIN: " + vehicle.getVin());
            System.out.println("Customer: " + name + " (" + email + ")");
            System.out.println("Total Lease Price: $" + String.format("%.2f", leaseContract.getTotalPrice()));
            System.out.println("Monthly Payment: $" + String.format("%.2f", leaseContract.getMonthlyPayment()));
            System.out.println("Lease Term: 36 months");
            System.out.print("Confirm this lease? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if(confirm.equals("y") || confirm.equals("yes")) {
                leaseDao.addLeaseContract(leaseContract);
                vehicleDao.removeVehicle(vin);
                System.out.println("✓ Lease completed successfully!");
            } else {
                System.out.println("Lease cancelled.");
            }
        }
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
        System.out.println("Are you sure you want to remove vehicle " + vin + "?");
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

    private Vehicle findVehicleByVin(String vin) {
        ArrayList<Vehicle> allVehicles = vehicleDao.getAllVehicles();
        for (Vehicle vehicle : allVehicles) {
            if (vehicle.getVin().equals(vin)) {
                return vehicle;
            }
        }
        return null;
    }

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