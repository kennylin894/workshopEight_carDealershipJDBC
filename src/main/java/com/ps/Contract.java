package com.ps;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Contract {
    private int id;
    private String date;
    private String customerName;
    private String customerEmail;
    private Vehicle vehicleChoosen;
    private double totalPrice;
    private double monthlyPayment;

    public Contract(String date, String customerName, String customerEmail, Vehicle vehicleChoosen) {
        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehicleChoosen = vehicleChoosen;
    }

    public Contract(int id, String date, String customerName, String customerEmail, Vehicle vehicleChoosen) {
        this.id = id;
        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehicleChoosen = vehicleChoosen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract double getTotalPrice();

    public abstract double getMonthlyPayment();

    public Vehicle getVehicleChoosen() {
        return vehicleChoosen;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public static void addToContractFiles(Contract contract)
    {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("contracts.csv",true));
//            SALE|20210928|Dana Wyatt|dana@texas.com|10112|1993|
//                    Ford|Explorer|SUV|Red|525123|995.00|
//                    49.75|100.00|295.00|1439.75|NO|0.00
//            LEASE|20210928|Zachary Westly|zach@texas.com|37846|2021|
//                    Chevrolet|Silverado|truck|Black|2750|31995.00|
//                    15997.50|2239.65|18337.15|541.39
            if(contract instanceof SalesContract)
            {
                Boolean financed = ((SalesContract) contract).isFinanced();
                String isFinanced = "NO";
                if(financed)
                {
                    isFinanced = "YES";
                }
                String firstLine = String.format("SALE|%s|%s|%s|%d|%d\n",
                        contract.getDate(),
                        contract.getCustomerName(),
                        contract.getCustomerEmail(),
                        contract.getVehicleChoosen().getVin(),
                        contract.getVehicleChoosen().getYear());
                String secondLine = String.format("%s|%s|%s|%s|%.2f\n",
                        contract.getVehicleChoosen().getMake(),
                        contract.getVehicleChoosen().getModel(),
                        contract.getVehicleChoosen().getColor(),
                        contract.getVehicleChoosen().getOdometer(),
                        contract.getVehicleChoosen().getPrice());
                String thirdLine = String.format("%.2f|%.2f|%.2f|%.2f|%s|%.2f\n",
                        ((SalesContract) contract).getRecordingFee(),
                        ((SalesContract) contract).getProcessingFee(),
                        ((SalesContract) contract).getSalesTax(),
                        contract.getTotalPrice(),
                        isFinanced,
                        contract.getMonthlyPayment());
                bufferedWriter.write(firstLine);
                bufferedWriter.write(secondLine);
                bufferedWriter.write(thirdLine);
            }
            else if(contract instanceof LeaseContract)
            {
                String firstLine = String.format("LEASE|%s|%s|%s|%d|%d\n",
                        contract.getDate(),
                        contract.getCustomerName(),
                        contract.getCustomerEmail(),
                        contract.getVehicleChoosen().getVin(),
                        contract.getVehicleChoosen().getYear());
                String secondLine = String.format("%s|%s|%s|%s|%d|%.2f\n",
                        contract.getVehicleChoosen().getMake(),
                        contract.getVehicleChoosen().getModel(),
                        contract.getVehicleChoosen().getVehicleType(),
                        contract.getVehicleChoosen().getColor(),
                        contract.getVehicleChoosen().getOdometer(),
                        contract.getVehicleChoosen().getPrice());
                String thirdLine = String.format("%.2f|%.2f|%.2f|%.2f\n",
                        ((LeaseContract) contract).getExpectedEndingValue(),
                        ((LeaseContract) contract).getLeaseFee(),
                        contract.getTotalPrice(),
                        contract.getMonthlyPayment());
                bufferedWriter.write(firstLine);
                bufferedWriter.write(secondLine);
                bufferedWriter.write(thirdLine);
            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
