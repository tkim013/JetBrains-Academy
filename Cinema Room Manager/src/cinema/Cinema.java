package cinema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Cinema {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        start();
    }

    public static void printSeats() {

        System.out.println("Cinema:");
        System.out.println("  1 2 3 4 5 6 7 8");

        for (int i = 1; i <= 7; i++) {
            System.out.print(i);
            for (int j = 0; j < 8; j++) {
                System.out.print(" S");
            }
            System.out.println();
        }
    }

    public static void calcProfit() {

        int rows = 0;
        int seats = 0;
        int price = 0;

        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        seats = scanner.nextInt();

        System.out.println("Total income:");

        if (rows * seats <= 60) {
            price = rows * seats * 10;
        } else {
            int firstHalf = rows / 2;
            price = (firstHalf * seats * 10) + ((rows - firstHalf) * seats * 8);
        }

        System.out.println("$" + price);
    }

    public static void ticketSeating() {
        int rows = 0;
        int seats = 0;
        int price = 0;

        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        seats = scanner.nextInt();

        seatingPrint(rows, seats, -1, -1);

        System.out.println("Enter a row number:");
        int row = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int seat = scanner.nextInt();
        if (rows * seats <= 60) {
            price = 10;
        } else {
            price = row <= rows / 2 ? 10 : 8;
        }
        System.out.println("Ticket price: $" + price);

        seatingPrint(rows, seats, row, seat);
    }

    public static void seatingPrint(int rows, int seats, int row, int seat) {
        System.out.println("Cinema:");
        System.out.print(" ");
        for (int i = 1; i <= seats; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        for (int i = 1; i <= rows; i++) {
            System.out.print(i);
            for (int j = 0; j < seats; j++) {

                if (i == row && j == seat - 1) {
                    System.out.print(" B");
                } else {
                    System.out.print(" S");
                }
            }
            System.out.println();
        }
    }

    public static void start() {

        int rows;
        int seats;
        int purchased = 0;
        int income = 0;
        int totalIncome = 0;

        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        seats = scanner.nextInt();

        char[][] seating = new char[rows][seats];

        //Fill each row with 'S'
        for (char[] row : seating)
            Arrays.fill(row, 'S');

        if (rows * seats <= 60) {
            totalIncome = rows * seats * 10;
        } else {
            totalIncome = (rows / 2 * seats * 10) + (rows - rows / 2) * seats * 8;
        }

        menu(seating, purchased, income, totalIncome);
    }

    public static void showSeats(char[][] seating) {

        System.out.println("Cinema:");
        System.out.print(" ");
        for (int i = 0; i < seating[0].length; i++) {
            System.out.print(" " + (i + 1));
        }
        System.out.println();
        for (int i = 0; i < seating.length; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < seating[0].length; j++) {

                System.out.print(" " + seating[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void menu(char[][] seating,
                            int purchased,
                            int income,
                            int totalIncome
    ) {

        int option;

        while (true) {
            System.out.println("1. Show the seats\n" +
                    "2. Buy a ticket\n" +
                    "3. Statistics\n" +
                    "0. Exit");
            option = scanner.nextInt();

            switch (option) {
                case 1 :
                    showSeats(seating);
                    break;
                case 2 :
                    int[] ret;
                    ret = buyTicket(seating, purchased, income);
                    purchased = ret[0];
                    income = ret[1];
                    break;
                case 3 :
                    statistics(seating, purchased, income, totalIncome);
                    break;
                default :
                    break;
            }

            if (option == 0) {
                break;
            }
        }
    }

    public static int[] buyTicket(char[][] seating, int purchased, int income) {

        int row = -1;
        int seat = -1;
        int price;
        int[] ret = new int[2];

        while (true) {

            try {
                System.out.println("Enter a row number:");
                row = scanner.nextInt();
                System.out.println("Enter a seat number in that row:");
                seat = scanner.nextInt();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (row < 0 || row > seating.length || seat < 0 || seat > seating[0].length) {
                System.out.println("Wrong input!");
                continue;
            }

            if (seating[row - 1][seat - 1] == 'S') {
                if (seating.length * seating[0].length <= 60) {
                    price = 10;
                } else {
                    price = row <= seating.length / 2 ? 10 : 8;
                }
                System.out.println("Ticket price: $" + price);
                seating[row - 1][seat - 1] = 'B';
                ret[0] = purchased + 1;
                ret[1] = income + price;
                break;
            } else {
                System.out.println("That ticket has already been purchased!");
            }
        }
        return ret;
    }

    public static void statistics(char[][] seating,
                                  int purchased,
                                  int income,
                                  int totalIncome) {

        System.out.printf("Number of purchased tickets: %d%n", purchased);
        System.out.printf("Percentage: %.2f%%%n", (100 * (double)purchased / ((double)seating.length * (double)seating[0].length)));
        System.out.printf("Current income: $%d%n", income);
        System.out.printf("Total income: $%d%n%n", totalIncome);
    }
}