package com.enigma.repository.implementation;

import com.enigma.configuration.DbConnector;
import com.enigma.constant.Constant;
import com.enigma.entity.Customer;
import com.enigma.repository.CustomerRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepoImpl implements CustomerRepo {
    DbConnector dbConnector = new DbConnector();
    Connection conn;

    public CustomerRepoImpl() {
    }

    @Override
    public List<Customer> getAll() {
        conn = dbConnector.startConnect();
        List<Customer> data = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM m_customer");
            ResultSet result = ps.executeQuery();

            while(result.next()) {
                Integer id = result.getInt("id");
                String name = result.getString("name");
                String birth_date = result.getString("birth_date");

                data.add(new Customer(id, name, birth_date));
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
    public void save(Customer customer) {
        conn = dbConnector.startConnect();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO m_customer " +
                    "(name, birth_date) VALUES (?,?::date)");
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getBirth_date());
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
    public void update(Customer customer) {
        conn = dbConnector.startConnect();
        if (this.getById(customer.getId()) != null) {
            try {
                PreparedStatement ps = conn.prepareStatement("UPDATE m_customer SET name = ?, " +
                        "birth_date = ?::date, WHERE id = ?");
                ps.setString(1, customer.getName());
                ps.setString(2, customer.getBirth_date());
                ps.setInt(3, customer.getId());
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
                PreparedStatement ps = conn.prepareStatement("DELETE FROM m_customer WHERE id = ?");
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
    public Customer getById(Integer id) {
        conn = dbConnector.startConnect();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM m_customer WHERE id = ?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                String name = result.getString("name");
                String birthDate = result.getString("birth_date");
                return new Customer(id, name, birthDate);
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
