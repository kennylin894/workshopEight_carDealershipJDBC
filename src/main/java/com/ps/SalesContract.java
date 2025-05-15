package com.ps;

public class SalesContract extends Contract {

    private final double salesTax = 0.05;
    private final double recordingFee = 100;
    private double processingFee;
    private double originalPrice;
    private boolean isFinanced;


    public SalesContract(String date, String customerName, String customerEmail, Boolean vehicleSold, double originalPrice, boolean isFinanced) {
        super(date, customerName, customerEmail, vehicleSold);
        this.originalPrice = originalPrice;
        this.isFinanced = isFinanced;
        if(getOriginalPrice() < 10000)
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
        double totalPrice = getOriginalPrice() + (getOriginalPrice() * 0.05);
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
        double loanAmount = getOriginalPrice();
        double intrestRate;
        int months;
        if(getOriginalPrice() >= 10000)
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

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
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
}
