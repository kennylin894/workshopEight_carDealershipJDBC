package com.ps;

import org.apache.commons.dbcp2.BasicDataSource;

public class Main {
    public static void main(String[] args) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/car_dealership");
        basicDataSource.setUsername(args[0]);
        basicDataSource.setPassword(args[1]);

        VehicleDao vehicleDao = new VehicleDao(basicDataSource);
        try
        {
            Userinterface userinterface = new Userinterface(vehicleDao);
            userinterface.display();
        } catch (Exception e) {
            System.out.println("Error, bad input. Try again");
            System.out.println();
        }
    }
}