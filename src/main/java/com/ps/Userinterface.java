package com.ps;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Userinterface {
    private Dealership dealership;
    private Scanner scanner = new Scanner(System.in);

    private void init() {
        dealership = DealershipFileManager.getDealership();
    }

    public Userinterface() {
        init();
    }

    public void display() {

        System.out.println("Welcome to the dealership program.");
        System.out.println("==================================");
        System.out.println("What would you like to do?");
        int mainMenuCommand;

        do {
            System.out.println("[1] Get by price");
            System.out.println("[2] Get by make/model");
            System.out.println("[3] Get by year");
            System.out.println("[4] Get by color");
            System.out.println("[5] Get by mileage");
            System.out.println("[6] Get by type");
            System.out.println("[7] Get all");
            System.out.println("[8] Add vehicle");
            System.out.println("[9] Remove vehicle");
            System.out.println("[10] Sell/Leases a vehicle");
            System.out.println("[0] Exit");

            System.out.print("Command: ");
            mainMenuCommand = scanner.nextInt();

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
                //Todo
                case 10:
                    processContractVehicle();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Command not found, try again");
            }
        } while (mainMenuCommand != 0);
    }

    private void processContractVehicle() {
        //TODO
        System.out.println("Do you want to");
        System.out.println("[1] Sell");
        System.out.println("[2] Lease");
        int sellLease = scanner.nextInt();
        boolean isFinanced = false;
        if(sellLease != 1 && sellLease != 2)
        {
            System.out.println("Error, bad input. Try again.");
            return;
        }
        if(sellLease == 1)
        {
            System.out.println("Do you want to finance?");
            System.out.println("[1] Yes");
            System.out.println("[2] No");
            int  financeChoice = scanner.nextInt();
            if(financeChoice == 1)
            {
                isFinanced = true;
            }
        }
        System.out.println("What is the VIN of the vehicle?");
        int vin = scanner.nextInt();
        Vehicle vehicle = dealership.findVehicleByVin(vin);
        if(vehicle == null)
        {
            return;
        }
        if(sellLease == 2)
        {
            if(Year.now().getValue() - vehicle.getYear() > 3)
            {
                System.out.println("You cannot lease this vehicle that is more than 3 years old.");
                return;
            }
        }
        scanner.nextLine();
        System.out.println("What is your full name?");
        String name = scanner.nextLine();
        System.out.println("What is your email?");
        String email = scanner.nextLine();
        if(sellLease == 1)
        {
            LocalDate now = LocalDate.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String formattedDate = now.format(dateTimeFormatter);
            SalesContract salesContract = new SalesContract(formattedDate,name,email,vehicle,isFinanced);
            System.out.println("This is your totalPrice: $" + String.format("%.2f", salesContract.getTotalPrice()));
            if(isFinanced) {
                System.out.println("This is your monthly payment: $" + String.format("%.2f", salesContract.getMonthlyPayment()));
            }
            System.out.println("The vehicle is now yours!");
            System.out.println("=========================");
            System.out.println();
            Contract.addToContractFiles(salesContract);
            dealership.removeVehicle(vehicle);
        }
        else if(sellLease == 2)
        {
            LocalDate now = LocalDate.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String formattedDate = now.format(dateTimeFormatter);
            LeaseContract leaseContract = new LeaseContract(formattedDate,name,email,vehicle);
            System.out.println("This is your total lease price: $" + String.format("%.2f", leaseContract.getTotalPrice()));
            System.out.println("This is your total monthly payment: $ " + String.format("%.2f", leaseContract.getMonthlyPayment()));
            System.out.println("The vehicle is now yours!");
            System.out.println("=========================");
            System.out.println();
            Contract.addToContractFiles(leaseContract);
            dealership.removeVehicle(vehicle);
            DealershipFileManager.saveDealership(dealership);
        }
    }

    private void processGetByPriceRequest() {
        // ArrayList<Vehicle> filteredVehicles = dealership.getVehiclesByPrice(startingPrice, endingPrice);
        // Display vehicles with for loop
        System.out.println("You are now filtering your search by price");
        System.out.println("What is your min price?");
        System.out.println("Min: ");
        int min = scanner.nextInt();
        System.out.println("What is the max price?");
        System.out.println("Max: ");
        int max = scanner.nextInt();
        System.out.println("There are the vehicles between the price $" + min + " and $" + max);
        printingVehiclesOut(dealership.getVehiclesByPrice(min,max));
        System.out.println();
    }

    private void processGetByMakeModelRequest() {
        System.out.println("You are now filtering your search by model");
        System.out.println("These are all the makes.");
        System.out.println("========================");
        ArrayList<String> makes = new ArrayList<>();
        ArrayList<String> models = new ArrayList<>();
        for (Vehicle vehicle : dealership.getInventory()) {
            if(!makes.contains(vehicle.getMake()))
            {
                makes.add(vehicle.getMake());
                System.out.println(vehicle.getMake());
            }
        }
        System.out.println("========================");
        System.out.println("What is your desired make ");
        System.out.println("Make: ");
        scanner.nextLine();
        String make = scanner.nextLine();
        System.out.println("These are all the models from the make: " + make);
        System.out.println("What is your desired model from these makes");
        for(Vehicle vehicle: dealership.getInventory())
        {
            if(vehicle.getMake().equalsIgnoreCase(make))
            {
                if(!models.contains(vehicle.getModel()))
                {
                    models.add(vehicle.getModel());
                    System.out.println(vehicle.getModel());
                }
            }
        }
        System.out.println("Model: ");
        String model = scanner.nextLine();
        System.out.println("There are the vehicles with the model: " + make + " and make: " + model);
        printingVehiclesOut(dealership.getVehiclesByMakeModel(make, model));
        System.out.println();
    }

    private void processGetByYearRequest() {
        System.out.println("You are now filtering your search by year");
        System.out.println("What is your starting year?");
        System.out.println("Starting Year: ");
        int min = scanner.nextInt();
        System.out.println("What is the end year?");
        System.out.println("End Year: ");
        int max = scanner.nextInt();
        System.out.println("There are the vehicles between the year " + min + " and " + max);
        printingVehiclesOut(dealership.getVehiclesByYear(min, max));
        System.out.println();
    }

    private void processGetByColorRequest() {
        System.out.println("You are now filtering your search by color");
        System.out.println("These are all the available colors");
        System.out.println("==================================");
        ArrayList<String> colors = new ArrayList<>();
        for (Vehicle vehicle : dealership.getInventory()) {
            if (!colors.contains(vehicle.getColor())) {
                colors.add(vehicle.getColor());
                System.out.println(vehicle.getColor());
            }
        }
        System.out.println("==================================");
        System.out.println("What color do you want?");
        System.out.println("Color: ");
        scanner.nextLine();
        String color = scanner.nextLine();
        System.out.println("These are the vehicles that are the color :" + color);
        printingVehiclesOut(dealership.getVehiclesByColor(color));
        System.out.println();
    }

    private void processGetByMileageRequest() {
        System.out.println("You are now filtering by mileage.");
        System.out.println("What is the min mileage?");
        System.out.println("Min Mileage: ");
        int min = scanner.nextInt();
        System.out.println("What is the max mileage?");
        System.out.println("Max Mileage: ");
        int max = scanner.nextInt();
        System.out.println("These are the cars with mileage between " + min + " and " + max + ".");
        printingVehiclesOut(dealership.getVehiclesByMileage(min, max));
        System.out.println();
    }

    private void processGetByVehicleTypeRequest() {
        System.out.println("You are now filtering by vehicle type.");
        System.out.println("These are all the vehicle types available.");
        System.out.println("==========================================");
        ArrayList<String> types = new ArrayList<>();
        for (Vehicle vehicle : dealership.getInventory()) {
            if (!types.contains(vehicle.getVehicleType())) {
                types.add(vehicle.getVehicleType());
                System.out.println(vehicle.getVehicleType());
            }
        }
        System.out.println("==========================================");
        System.out.println("What vehicle type?");
        System.out.println("Type: ");
        scanner.nextLine();
        String type = scanner.nextLine();
        System.out.println("These are all the cars of type: " + type);
        printingVehiclesOut(dealership.getVehiclesByType(type));
    }

    private void processGetAllVehiclesRequest() {
        System.out.println("These are all the vehicles we have currently.");
        printingVehiclesOut(dealership.getAllVehicles());
    }

    private void processAddVehicleRequest() {
        System.out.println("What is the vin of the vehicle?");
        int vin = scanner.nextInt();
        System.out.println("What is the year of the vehicle?");
        int year = scanner.nextInt();
        System.out.println("What is the make of the vehicle?");
        scanner.nextLine();
        String make = scanner.nextLine();
        System.out.println("What is the model of the vehicle?");
        String model = scanner.nextLine();
        System.out.println("What is the type of vehicle?");
        String type = scanner.nextLine();
        System.out.println("What is the color of the vehicle?");
        String color = scanner.nextLine();
        System.out.println("What is the odometer of the vehicle?");
        int odometer = scanner.nextInt();
        System.out.println("What is the price of the vehicle?");
        double price = scanner.nextDouble();
        dealership.addVehicle(new Vehicle(vin,year,make,model,type,color,odometer,price));
    }

    private void processRemoveVehicleRequest() {
        System.out.println("What is the vin of the vehicle?");
        int vin = scanner.nextInt();
        boolean found = false;
        for(Vehicle vehicle:dealership.getInventory())
        {
            if(vehicle.getVin() == vin)
            {
                dealership.removeVehicle(vehicle);
                found = true;
                break;
            }
        }
        if(found)
        {
            DealershipFileManager.saveDealership(dealership);
            System.out.println("Vehicle removed successfully.");
        }
        else
        {
            System.out.println("No vehicle found with VIN " + vin + ".");
        }
    }

    public void printingVehiclesOut(ArrayList<Vehicle> list) {
        String line = "+--------+------+------------+-----------+-------------+--------+------------+-------------+";
        System.out.println(line);
        System.out.printf("| %-6s | %-4s | %-10s | %-9s | %-11s | %-6s | %-10s | %-11s |\n",
                "VIN", "Year", "Make", "Model", "Type", "Color", "Odometer", "Price");
        System.out.println(line);
        if(list.isEmpty())
        {
            System.out.println("There are no available vehicles.");
        }
        for (Vehicle vehicle : list) {
            System.out.printf("| %-6d | %-4d | %-10s | %-9s | %-11s | %-6s | %-10d | $%-10.2f |\n",
                    vehicle.getVin(),
                    vehicle.getYear(),
                    vehicle.getMake(),
                    vehicle.getModel(),
                    vehicle.getVehicleType(),
                    vehicle.getColor(),
                    vehicle.getOdometer(),
                    vehicle.getPrice());
        }
        System.out.println(line);
        System.out.println();
    }
}