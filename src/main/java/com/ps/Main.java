package com.ps;

import org.apache.commons.dbcp2.BasicDataSource;

public class Main {
    public static void main(String[] args) {
        if(args.length < 2){
            System.out.println("Please provide username and password as arguments");
            System.exit(1);
        }
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/car_dealership");
        basicDataSource.setUsername(args[0]);
        basicDataSource.setPassword(args[1]);
        VehicleDao vehicleDao = new VehicleDao(basicDataSource);
        SalesDao salesDao = new SalesDao(basicDataSource, vehicleDao);
        LeaseDao leaseDao = new LeaseDao(basicDataSource, vehicleDao);
        try {
            Userinterface userinterface = new Userinterface(vehicleDao, salesDao, leaseDao);
            userinterface.display();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}