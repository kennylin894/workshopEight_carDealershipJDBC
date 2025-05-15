package com.ps;

public class LeaseContract extends Contract {
    private final double interestRate = 0.04;
    private final int months = 36;

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleChoosen) {
        super(date, customerName, customerEmail, vehicleChoosen);
    }

    public double getExpectedEndingValue() {
        return this.getVehicleChoosen().getPrice() *0.5;
    }

    public double getLeaseFee() {
        return this.getVehicleChoosen().getPrice() * 0.07;
    }

    @Override
    public double getTotalPrice() {
        return getExpectedEndingValue() + getLeaseFee();
    }

    @Override
    public double getMonthlyPayment() {
        double monthlyPayment = getTotalPrice() * ((interestRate/12) * Math.pow(1 + (interestRate/12),months))/(Math.pow(1 + (interestRate/12),months) -1);
        return monthlyPayment;
    }
}
