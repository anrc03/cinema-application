package com.enigma.view;

import com.enigma.constant.Constant;
import com.enigma.entity.Chair;
import com.enigma.entity.Customer;
import com.enigma.entity.Movie;
import com.enigma.repository.implementation.ChairRepoImpl;
import com.enigma.repository.implementation.MovieRepoImpl;
import com.enigma.util.Utility;

import java.sql.Connection;

public class View {
    private final MovieRepoImpl movieRepo;
    private final ChairRepoImpl chairRepo;


    public View() {
        this.movieRepo = new MovieRepoImpl();
        this.chairRepo = new ChairRepoImpl();
    }

    public void startApp() {
        while (true) {
            printMenu();
            switch (Utility.inputString("Choose menu: ").toLowerCase()) {
                case "1":
                    buyTicket();
                    break;
                case "2":
                    System.exit(0);
                    break;
                default:
                    System.out.println(Constant.INVALID_INPUT);
                    continue;
            }
            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("|======= CIAWI THEATRE =======|");
        System.out.println("| Good day to watch a movie!  |");
        System.out.println("| 1. Buy a ticket             |");
        System.out.println("| 2. Exit                     |");
        System.out.println("|=============================|");
    }

    private void printMovie() {
        System.out.println(" id          title            ");
        System.out.println("---------------------------------");
        for (Movie movie : movieRepo.getAll()) System.out.println(movie);
        System.out.println("---------------------------------");
    }

    private void printSeat() {
        System.out.println(" id   Seat Number  ");
        System.out.println("----------------------");
        for (Chair chair : chairRepo.getAll()) {
            if (chair.getId() > 10) System.out.println(chair);
            else System.out.println(" " + chair);
        }
        System.out.println("----------------------");
    }

    private void buyTicket() {
        String name = Utility.inputString("Please enter your name: ");
        String day = Utility.inputDay("Enter your day of birth (dd format): ");
        String month = Utility.inputMonth("Enter your month of birth (mm format): ");
        String year = Utility.inputYear("Enter your birth year (yyyy format): ");
        String birthdate = String.format("%s-%s-%s", year, month, day);
        printMovie();
        String movieChoice = Utility.inputMovie("Enter the ID of the movie you wish to see: ");
        Integer ratingId = movieRepo.getById(Integer.parseInt(movieChoice)).getRatingId();
        boolean isEligible = Utility.checkEligibility(ratingId, year, month, day);
        if (isEligible) {
            printSeat();
            String seat = Utility.inputSeat("Enter the ID of the seat you want: ");
            Utility.processTicket(new Customer(0,name,birthdate), movieChoice, seat);
        }
        else System.out.println(Constant.NOT_ELIGIBLE);
    }
}

