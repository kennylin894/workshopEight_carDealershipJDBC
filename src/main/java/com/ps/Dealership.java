package com.ps;

import java.util.ArrayList;
import java.util.Scanner;

public class Dealership {
    private String name;
    private String address;
    private String phone;
    private ArrayList<Vehicle> inventory;

    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<Vehicle> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Vehicle> inventory) {
        this.inventory = inventory;
    }

    public Vehicle findVehicleByVin(String vin)
    {
        for(Vehicle vehicle: getAllVehicles())
        {
            if(vehicle.getVin().equals(vin))
            {
                return vehicle;
            }
        }
        System.out.println("No vehicle with that vin was found. Please try again.");
        return null;
    }

    public ArrayList<Vehicle> getVehiclesByPrice(double min, double max) {
        ArrayList<Vehicle> sortedInventory = new ArrayList<>();
        for (Vehicle vehicle : inventory) {
            if (vehicle.getPrice() > min && vehicle.getPrice() < max) {
                sortedInventory.add(vehicle);
            }
        }
        return sortedInventory;
    }

    public ArrayList<Vehicle> getVehiclesByMakeModel(String make, String model) {
        ArrayList<Vehicle> sortedInventory = new ArrayList<>();
        for (Vehicle vehicle : inventory) {
            if (vehicle.getMake().equalsIgnoreCase(make) && vehicle.getModel().equalsIgnoreCase(model)) {
                sortedInventory.add(vehicle);
            }
        }
        return sortedInventory;
    }

    public ArrayList<Vehicle> getVehiclesByYear(int min, int max) {
        ArrayList<Vehicle> sortedInventory = new ArrayList<>();
        for (Vehicle vehicle : inventory) {
            if (vehicle.getYear() > min && vehicle.getYear() < max) {
                sortedInventory.add(vehicle);
            }
        }
        return sortedInventory;
    }

    public ArrayList<Vehicle> getVehiclesByColor(String color) {
        ArrayList<Vehicle> sortedInventory = new ArrayList<>();
        for (Vehicle vehicle : inventory) {
            if (vehicle.getColor().equalsIgnoreCase(color)) {
                sortedInventory.add(vehicle);
            }
        }
        return sortedInventory;
    }

    public ArrayList<Vehicle> getVehiclesByMileage(int min, int max) {
        ArrayList<Vehicle> sortedInventory = new ArrayList<>();
        for (Vehicle vehicle : inventory) {
            if (vehicle.getOdometer() > min && vehicle.getOdometer() < max) {
                sortedInventory.add(vehicle);
            }
        }
        return sortedInventory;
    }

    public ArrayList<Vehicle> getVehiclesByType(String vehicleTpye) {
        ArrayList<Vehicle> sortedInventory = new ArrayList<>();
        for (Vehicle vehicle : inventory) {
            if (vehicle.getVehicleType().equalsIgnoreCase(vehicleTpye)) {
                sortedInventory.add(vehicle);
            }
        }
        return sortedInventory;
    }

    public ArrayList<Vehicle> getAllVehicles() {
        ArrayList<Vehicle> sortedInventory = new ArrayList<>();
        for (Vehicle vehicle : inventory) {
            sortedInventory.add(vehicle);
        }
        return sortedInventory;
    }

    public void addVehicle(Vehicle vehicle) {
        inventory.add(vehicle);
        DealershipFileManager.saveDealership(this);
        System.out.println("Vehicle added successfully.");
        System.out.println();
    }

    public void removeVehicle(Vehicle vehicle) {
        inventory.remove(vehicle);
    }
}
