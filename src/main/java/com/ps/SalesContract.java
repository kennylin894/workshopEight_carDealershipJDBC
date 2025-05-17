package com.ps;

public class SalesContract extends Contract {

    private final double salesTax = 0.05;
    private final double recordingFee = 100;
    private double processingFee;
    private boolean isFinanced;


    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleChoosen, boolean isFinanced) {
        super(date, customerName, customerEmail, vehicleChoosen);
        this.isFinanced = isFinanced;
        if(vehicleChoosen.getPrice() < 10000)
        {
            this.processingFee = 295;
        }
        else
        {
            this.processingFee = 495;
        }
    }

    @Override
    public double getTotalPrice() {
        double totalPrice = getVehicleChoosen().getPrice() + (getVehicleChoosen().getPrice() * 0.05);
        totalPrice += recordingFee;
        totalPrice += processingFee;
        return totalPrice;
    }

    @Override
    public double getMonthlyPayment() {
        if(!isFinanced)
        {
            return 0.0;
        }
        double loanAmount = getVehicleChoosen().getPrice();
        double intrestRate;
        int months;
        if(getVehicleChoosen().getPrice() >= 10000)
        {
            intrestRate = 0.0425;
            months = 48;
        }
        else
        {
            intrestRate = 0.0525;
            months = 24;
        }
        double monthlyPayment = loanAmount * (intrestRate/12)/(1 - Math.pow(1 + (intrestRate/12),-months));
        return monthlyPayment;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }

    public boolean isFinanced() {
        return isFinanced;
    }

    public void setFinanced(boolean financed) {
        isFinanced = financed;
    }

    public double getSalesTax() {
        return salesTax;
    }

    public double getRecordingFee() {
        return recordingFee;
    }
}
