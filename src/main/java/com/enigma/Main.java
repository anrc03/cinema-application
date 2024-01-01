package com.enigma;

import com.enigma.entity.Chair;
import com.enigma.entity.Movie;
import com.enigma.entity.Rating;
import com.enigma.entity.Theater;
import com.enigma.repository.implementation.ChairRepoImpl;
import com.enigma.repository.implementation.MovieRepoImpl;
import com.enigma.repository.implementation.RatingRepoImpl;
import com.enigma.repository.implementation.TheaterRepoImpl;
import com.enigma.view.View;

public class Main {
    public static void main(String[] args) {
//        RatingRepoImpl ratingRepo = new RatingRepoImpl();
//        MovieRepoImpl movieRepo = new MovieRepoImpl();
//        TheaterRepoImpl theaterRepo = new TheaterRepoImpl();
//        ChairRepoImpl chairRepo = new ChairRepoImpl();
        View view = new View();

        //rating
//        ratingRepo.save(new Rating(0,"A", "All Ages Admitted"));
//        ratingRepo.save(new Rating(0,"BO", "Some material may be inappropriate for children under 13"));
//        ratingRepo.save(new Rating(0,"R", "Under 18 requires accompanying parent or adult guardian"));
//        ratingRepo.save(new Rating(0,"D", "Adults Only. No one under 21 admitted"));

        //movie
//        movieRepo.save(new Movie(0,"Oppenheimer",180,"2023-07-19",50000,3));
//        movieRepo.save(new Movie(0,"The Marvels",105,"2023-10-7",50000,2));
//        movieRepo.save(new Movie(0,"Blue Beetle",127,"2023-08-18",50000,2));
//        movieRepo.save(new Movie(0,"Barbie",114,"2023-07-21",50000,2));
//        movieRepo.save(new Movie(0,"Spider-Man: Across the Spider-Verse",140,"2023-05-31",50000,1));

        //theater
//        theaterRepo.save(new Theater(0,"1",4,1));
//        theaterRepo.save(new Theater(0,"2",4,2));
//        theaterRepo.save(new Theater(0,"3",4,3));
//        theaterRepo.save(new Theater(0,"4",4,4));
//        theaterRepo.save(new Theater(0,"5",4,5));

        //chair
//        chairRepo.save(new Chair(0,"A1",1));
//        chairRepo.save(new Chair(0,"A2",1));
//        chairRepo.save(new Chair(0,"B1",1));
//        chairRepo.save(new Chair(0,"B2",1));
//        chairRepo.save(new Chair(0,"C1",2));
//        chairRepo.save(new Chair(0,"C2",2));
//        chairRepo.save(new Chair(0,"D1",2));
//        chairRepo.save(new Chair(0,"D2",2));
//        chairRepo.save(new Chair(0,"E1",3));
//        chairRepo.save(new Chair(0,"E2",3));
//        chairRepo.save(new Chair(0,"F1",3));
//        chairRepo.save(new Chair(0,"F2",3));
//        chairRepo.save(new Chair(0,"G1",4));
//        chairRepo.save(new Chair(0,"G2",4));
//        chairRepo.save(new Chair(0,"H1",4));
//        chairRepo.save(new Chair(0,"H2",4));
//        chairRepo.save(new Chair(0,"I1",5));
//        chairRepo.save(new Chair(0,"I2",5));
//        chairRepo.save(new Chair(0,"J1",5));
//        chairRepo.save(new Chair(0,"J2",5));

        view.startApp();
    }
}