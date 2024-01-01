package com.enigma.repository.implementation;

import com.enigma.configuration.DbConnector;
import com.enigma.constant.Constant;
import com.enigma.entity.Chair;
import com.enigma.entity.Theater;
import com.enigma.repository.TheaterRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TheaterRepoImpl implements TheaterRepo {
    DbConnector dbConnector = new DbConnector();
    Connection conn;

    public TheaterRepoImpl() {
    }

    @Override
    public List<Theater> getAll() {
        conn = dbConnector.startConnect();
        List<Theater> data = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM t_theater");
            ResultSet result = ps.executeQuery();

            while(result.next()) {
                Integer id = result.getInt("id");
                String theaterNum = result.getString("theater_number");
                Integer stock = result.getInt("stock");
                Integer filmId = result.getInt("film_id");

                data.add(new Theater(id, theaterNum, stock, filmId));
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
    public void save(Theater theater) {
        conn = dbConnector.startConnect();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO t_theater " +
                    "(theater_number, stock, film_id) VALUES (?,?,?)");
            ps.setString(1, theater.getTheaterNumber());
            ps.setInt(2, theater.getStock());
            ps.setInt(3, theater.getFilmId());
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
    public void update(Theater theater) {
        conn = dbConnector.startConnect();
        if (this.getById(theater.getId()) != null) {
            try {
                PreparedStatement ps = conn.prepareStatement("UPDATE t_theater SET theater_number = ?, " +
                        "stock = ?, film_id = ? WHERE id = ?");
                ps.setString(1, theater.getTheaterNumber());
                ps.setInt(2, theater.getStock());
                ps.setInt(3, theater.getFilmId());
                ps.setInt(4, theater.getId());
                ps.executeUpdate();
                System.out.println(Constant.UPDATE_SUCCESSFUL);
                dbConnector.closeConnection(ps, conn);
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
                PreparedStatement ps = conn.prepareStatement("DELETE FROM t_theater WHERE id = ?");
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
    public Theater getById(Integer id) {
        conn = dbConnector.startConnect();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM t_theater WHERE id = ?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                String theaterNum = result.getString("theater_number");
                Integer stock = result.getInt("stock");
                Integer filmId = result.getInt("film_id");
                return new Theater(id, theaterNum, stock, filmId);
            }
            dbConnector.closeConnection(result, ps, conn);
        }
        catch (SQLException e) {
            System.out.println(Constant.SELECT_ID_UNSUCCESSFUL);
            System.out.println(e.getMessage());
        }
        System.out.println(Constant.ID_NOT_FOUND);
        return null;
    }
}
