package com.ps;

public class SalesContract extends Contract {

    public SalesContract(String date, String customerName, String customerEmail, Boolean vehicleSold) {
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
