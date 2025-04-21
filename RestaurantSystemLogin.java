/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.restaurantsystemlogin;

/**
 *
 * @author BiostarB450MHPver6.3
 */

import java.io.*;
import java.util.Scanner;

public class RestaurantSystemLogin {

    static Scanner scanner = new Scanner(System.in);
    static String[] items = {"Adobong Chicken", "Beef Steak", "Steamed Fish"};
    static int[] prices = {70, 80, 50};
    static int[] quantities = {0, 0, 0};

    public static void main(String[] args) {
        System.out.println("Welcome to the Restaurant System");
        System.out.println("1. Register\n2. Login");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            createAccount();
        } else if (choice == 2) {
            if (login()) {
                orderingMenu();
            }
        } else {
            System.out.println("Invalid option.");
        }
    }

    public static void createAccount() {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            String encryptedPassword = caesarEncrypt(password);

            FileWriter fw = new FileWriter("users.txt", true);
            fw.write(username + "," + encryptedPassword + "\n");
            fw.close();

            System.out.println("Account created successfully!");
        } catch (IOException e) {
            System.out.println("Error creating account.");
        }
    }

    static boolean login() {
        System.out.print("Enter username: ");
        String inputUser = scanner.nextLine();
        System.out.print("Enter password: ");
        String inputPass = scanner.nextLine();

        try {
            FileReader fr = new FileReader("users.txt");
            BufferedReader read = new BufferedReader(fr);
            String line;
            while ((line = read.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                String encryptedPassword = parts[1];
                String decryptedPassword = caesarDecrypt(encryptedPassword);

                if (inputUser.equals(username) && inputPass.equals(decryptedPassword)) {
                    System.out.println("Login successful!");
                    return true;
                }
            }
            read.close();
        } catch (IOException e) {
            System.out.println("Error during login.");
        }

        System.out.println("Login failed. Invalid username or password.");
        return false;
    }

    public static void orderingMenu() {
        int option;
        do {
            System.out.println("\n--- Menu ---");
            for (int i = 0; i < items.length; i++) {
                System.out.println((i + 1) + ". " + items[i] + " - $" + prices[i]);
            }
            System.out.println("4. Exit and Checkout");
            System.out.print("Choose an item: ");
            option = scanner.nextInt();

            if (option >= 1 && option <= 3) {
                System.out.print("Enter quantity: ");
                int qty = scanner.nextInt();
                quantities[option - 1] += qty;
            } else if (option != 4) {
                System.out.println("Invalid option.");
            }
        } while (option != 4);

        orderDetails();
    }

    public static void orderDetails() {
        int total = 0;
        System.out.println("\n--- Order Summary ---");
        for (int i = 0; i < items.length; i++) {
            if (quantities[i] > 0) {
                int itemTotal = quantities[i] * prices[i];
                System.out.println(items[i] + " x" + quantities[i] + " = " + itemTotal + " pesos");
                total += itemTotal;
            }
        }
        System.out.println("Total Bill: " + total + "pesos");
    }

   public static String caesarEncrypt(String input) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : input.toCharArray()) {
            encrypted.append((char) (c + 3));
        }
        return encrypted.toString();
    }

    static String caesarDecrypt(String input) {
        StringBuilder decrypted = new StringBuilder();
        for (char c : input.toCharArray()) {
            decrypted.append((char) (c - 3));
        }
        return decrypted.toString();
    }
}

