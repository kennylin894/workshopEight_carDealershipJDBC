package com.ps;

public class SalesContract extends Contract {

    private final double salesTax = 0.05;
    private final double recordingFee = 100;
    private double processingFee;
    private double originalPrice;

    private boolean isFinanced;


    public SalesContract(String date, String customerName, String customerEmail, Boolean vehicleSold, double originalPrice) {
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
