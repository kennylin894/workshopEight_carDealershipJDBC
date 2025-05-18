package com.ps;

public class Main {
    public static void main(String[] args) {
        try
        {
            Userinterface userinterface = new Userinterface();
            userinterface.display();
        } catch (Exception e) {
            System.out.println("Error, bad input. Try again");
            System.out.println();
        }
    }
}