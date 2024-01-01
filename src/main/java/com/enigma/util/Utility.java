package com.enigma.util;

import com.enigma.configuration.DbConnector;
import com.enigma.constant.Constant;
import com.enigma.entity.Customer;
import com.enigma.entity.Theater;
import com.enigma.entity.Transaction;
import com.enigma.repository.implementation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Utility {

    public static String inputString(String text) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(text);
            String input = scanner.nextLine();
            if (input.isBlank()) continue;
            return input;
        }
    }

    public static String inputDay(String text) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(text);
            String input = scanner.nextLine();
            if (input.isBlank() || !(input.matches("[0-3][0-9]")) || Integer.parseInt(input) > 31 || input.equals("00")) continue;
            return input;
        }
    }

    public static String inputMonth(String text) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(text);
            String input = scanner.nextLine();
            if (input.isBlank() || !(input.matches("0[1-9]|1[1,2]"))) continue;
            return input;
        }
    }

    public static String inputYear(String text) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(text);
            String input = scanner.nextLine();
            if (input.isBlank() || !(input.matches("19[0-9][0-9]|20[0-9][0-9]"))) continue;
            return input;
        }
    }

    public static String inputMovie(String text) {
        MovieRepoImpl movieRepo = new MovieRepoImpl();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(text);
            String input = scanner.nextLine();
            Integer movieId = Integer.parseInt(input);
            if (input.isBlank() || !(input.matches("[0-9]|[0-9][0-9]")) || movieRepo.getById(movieId) == null) continue;
            return input;
        }
    }

    private static String checkRating (Integer ratingId) {
        String rating = null;
        if (ratingId == 1) rating = "A";
        if (ratingId == 2) rating = "BO";
        if (ratingId == 3) rating = "R";
        if (ratingId == 4) rating = "D";
        return rating;
    }
    public static boolean checkEligibility(Integer ratingId, String year, String month, String day) {
        LocalDate birthdate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        LocalDate today = LocalDate.now();
        long age = ChronoUnit.YEARS.between(birthdate, today);
        String rating = checkRating(ratingId);
        if (rating.equals("A")) return true;
        if (rating.equals("D") && age >= 21) return true;
        if (rating.equals("R") && age >= 18) return true;
        return rating.equals("BO") && age >= 13;
    }

    public static String inputSeat(String text) {
        ChairRepoImpl chairRepo = new ChairRepoImpl();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(text);
            String input = scanner.nextLine();
            Integer seatId = Integer.parseInt(input);
            if (input.isBlank() || !(input.matches("[0-3][0-9]|[0-9]")) || chairRepo.getById(seatId) == null) continue;
            return input;
        }
    }

    public static void processTicket(Customer customer, String movieChoice, String seat) {
        DbConnector dbConnector = new DbConnector();
        Connection conn = dbConnector.startConnect();
        CustomerRepoImpl customerRepo = new CustomerRepoImpl();
        TheaterRepoImpl theaterRepo = new TheaterRepoImpl();
        TransactionRepoImpl transactionRepo = new TransactionRepoImpl();
        Integer movieId = Integer.parseInt(movieChoice);
        Integer seatId = Integer.parseInt(seat);
        try {
            conn.setAutoCommit(false);
            for (Theater theater : theaterRepo.getAll()) {
                if (theater.getFilmId() == movieId) {
                    if (theater.getStock() > 0) {
                        customerRepo.save(new Customer(0,customer.getName(), customer.getBirth_date()));
                        for (Customer customer1 : customerRepo.getAll()) {
                            if (customer1.getName().equals(customer.getName())) {
                                Integer customer1Id = customer1.getId();
                                transactionRepo.save(new Transaction(0,seatId, customer1Id));
                            }
                        }
                        theaterRepo.update(new Theater(theater.getId(), theater.getTheaterNumber(), theater.getStock() - 1, theater.getFilmId()));
                    }
                }
            }
            conn.commit();
            System.out.println(Constant.TRX_SUCCESSFUL);
        }
        catch (SQLException e) {
            try {
                System.out.println(Constant.TRX_UNSUCCESSFUL);
                System.err.print("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println(e.getMessage());
        }
    }
}
