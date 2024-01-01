package com.enigma.repository.implementation;

import com.enigma.configuration.DbConnector;
import com.enigma.constant.Constant;
import com.enigma.entity.Chair;
import com.enigma.repository.ChairRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChairRepoImpl implements ChairRepo {
    DbConnector dbConnector = new DbConnector();
    Connection conn;

    public ChairRepoImpl() {
    }

    @Override
    public List<Chair> getAll() {
        conn = dbConnector.startConnect();
        List<Chair> data = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM t_seat");
            ResultSet result = ps.executeQuery();

            while(result.next()) {
                Integer id = result.getInt("id");
                String seatNum = result.getString("seat_number");
                Integer theaterId = result.getInt("theater_id");

                data.add(new Chair(id, seatNum, theaterId));
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
    public void save(Chair chair) {
        conn = dbConnector.startConnect();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO t_seat " +
                    "(seat_number, theater_id) VALUES (?,?)");
            ps.setString(1, chair.getSeatNumber());
            ps.setInt(2, chair.getTheaterId());
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
    public void update(Chair chair) {
        conn = dbConnector.startConnect();
        if (this.getById(chair.getId()) != null) {
            try {
                PreparedStatement ps = conn.prepareStatement("UPDATE t_seat SET seat_number = ?, " +
                        "theater_id = ?, WHERE id = ?");
                ps.setString(1, chair.getSeatNumber());
                ps.setInt(2, chair.getTheaterId());
                ps.setInt(3, chair.getId());
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
                PreparedStatement ps = conn.prepareStatement("DELETE FROM t_seat WHERE id = ?");
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
    public Chair getById(Integer id) {
        conn = dbConnector.startConnect();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM t_seat WHERE id = ?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                String seatNum = result.getString("seat_number");
                Integer theaterId = result.getInt("theater_id");
                return new Chair(id, seatNum, theaterId);
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
