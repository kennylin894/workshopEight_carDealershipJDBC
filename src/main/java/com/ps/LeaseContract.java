package com.ps;

public class LeaseContract extends Contract {

    public LeaseContract(String date, String customerName, String customerEmail, Boolean vehicleSold) {
        super(date, customerName, customerEmail, vehicleSold);
    }

    @Override
    public double getTotalPrice() {
        return 0;
    }

    @Override
    public double getMonthlyPayment() {
        return 0;
    }
}
