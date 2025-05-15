package com.ps;

public class LeaseContract extends Contract {

    private double originalPrice;

    public LeaseContract(String date, String customerName, String customerEmail, Boolean vehicleSold,double originalPrice) {
        super(date, customerName, customerEmail, vehicleSold);
        this.originalPrice = originalPrice;
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
