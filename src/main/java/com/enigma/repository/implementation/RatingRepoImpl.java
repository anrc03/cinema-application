package com.enigma.repository.implementation;

import com.enigma.configuration.DbConnector;
import com.enigma.constant.Constant;
import com.enigma.entity.Rating;
import com.enigma.repository.RatingRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RatingRepoImpl implements RatingRepo {
    DbConnector dbConnector = new DbConnector();
    Connection conn;

    public RatingRepoImpl() {
    }

    @Override
    public List<Rating> getAll() {
        conn = dbConnector.startConnect();
        List<Rating> data = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM t_rating");
            ResultSet result = ps.executeQuery();

            while(result.next()) {
                Integer id = result.getInt("id");
                String code = result.getString("code");
                String desc = result.getString("description");

                data.add(new Rating(id, code, desc));
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
    public void save(Rating rating) {
        conn = dbConnector.startConnect();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO t_rating " +
                    "(code, description) VALUES (?,?)");
            ps.setString(1, rating.getCode());
            ps.setString(2, rating.getDescription());
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
    public void update(Rating rating) {
        conn = dbConnector.startConnect();
        if (this.getById(rating.getId()) != null) {
            try {
                PreparedStatement ps = conn.prepareStatement("UPDATE t_rating SET code = ?, " +
                        "description = ?, WHERE id = ?");
                ps.setString(1, rating.getCode());
                ps.setString(2, rating.getDescription());
                ps.setInt(3, rating.getId());
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
                PreparedStatement ps = conn.prepareStatement("DELETE FROM t_rating WHERE id = ?");
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
    public Rating getById(Integer id) {
        conn = dbConnector.startConnect();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM t_rating WHERE id = ?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                String code = result.getString("code");
                String desc = result.getString("description");
                return new Rating(id, code, desc);
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
