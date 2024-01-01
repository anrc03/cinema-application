package com.enigma.repository.implementation;

import com.enigma.configuration.DbConnector;
import com.enigma.constant.Constant;
import com.enigma.entity.Movie;
import com.enigma.repository.MovieRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieRepoImpl implements MovieRepo {
    DbConnector dbConnector = new DbConnector();
    Connection conn;

    public MovieRepoImpl() {
    }

    @Override
    public List<Movie> getAll() {
        conn = dbConnector.startConnect();
        List<Movie> data = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM t_film");
            ResultSet result = ps.executeQuery();

            while(result.next()) {
                Integer id = result.getInt("id");
                String title = result.getString("title");
                Integer duration = result.getInt("duration");
                String date = result.getString("show_date");
                Integer price = result.getInt("price");
                Integer ratingId = result.getInt("rating_id");

                data.add(new Movie(id, title, duration, date, price, ratingId));
            }
            dbConnector.closeConnection(result,ps, conn);
        }
        catch (SQLException e) {
            System.out.println(Constant.SELECT_ALL_UNSUCCESSFUL);
            System.out.println(e.getMessage());
        }
        return data;
    }

    @Override
    public void save(Movie movie) {
        conn = dbConnector.startConnect();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO t_film " +
                    "(title, duration, show_date, price, rating_id) " +
                    "VALUES (?,?,?::date,?,?)");
            ps.setString(1, movie.getTitle());
            ps.setInt(2, movie.getDuration());
            ps.setString(3, movie.getShowDate());
            ps.setInt(4, movie.getPrice());
            ps.setInt(5, movie.getRatingId());
            ps.executeUpdate();
            System.out.println(Constant.INSERT_SUCCESSFUL);
            dbConnector.closeConnection(ps, conn);
        }
        catch (SQLException e){
            System.out.println(Constant.INSERT_UNSUCCESSFUL);
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Movie movie) {
        conn = dbConnector.startConnect();
        if (this.getById(movie.getId()) != null) {
            try {
                PreparedStatement ps = conn.prepareStatement("UPDATE t_film SET title = ?, " +
                        "duration = ?, show_date = ?::date, price = ?, rating_id = ? WHERE id = ?");
                ps.setString(1, movie.getTitle());
                ps.setInt(2, movie.getDuration());
                ps.setString(3, movie.getShowDate());
                ps.setInt(4, movie.getPrice());
                ps.setInt(5, movie.getRatingId());
                ps.setInt(6, movie.getId());
                ps.executeUpdate();
                System.out.println(Constant.UPDATE_SUCCESSFUL);
                dbConnector.closeConnection(ps,conn);
            }
            catch (SQLException e) {
                System.out.println(Constant.UPDATE_UNSUCCESSFUL);
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void delete(Integer id) {
        conn = dbConnector.startConnect();
        if ((this.getById(id) != null)) {
            try {
                PreparedStatement ps = conn.prepareStatement("DELETE FROM t_film WHERE id = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
                System.out.println(Constant.DELETE_SUCCESSFUL);
                dbConnector.closeConnection(ps,conn);
            }
            catch (SQLException e) {
                System.out.println(Constant.DELETE_UNSUCCESSFUL);
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public Movie getById(Integer id) {
        conn = dbConnector.startConnect();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM t_film WHERE id = ?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                String title = result.getString("title");
                Integer duration = result.getInt("duration");
                String date = result.getString("show_date");
                Integer price = result.getInt("price");
                Integer ratingId = result.getInt("rating_id");
                return new Movie(id, title, duration, date, price, ratingId);
            }
            dbConnector.closeConnection(result, ps,conn);
        }
        catch (SQLException e) {
            System.out.println(Constant.SELECT_ID_UNSUCCESSFUL);
            System.out.println(e.getMessage());
        }
        System.out.println(Constant.ID_NOT_FOUND);
        return null;
    }
}
