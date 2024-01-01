package com.enigma.repository.implementation;

import com.enigma.configuration.DbConnector;
import com.enigma.constant.Constant;
import com.enigma.entity.Theater;
import com.enigma.entity.Transaction;
import com.enigma.repository.TransactionRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepoImpl implements TransactionRepo {
    DbConnector dbConnector = new DbConnector();
    Connection conn;

    public TransactionRepoImpl() {
    }

    @Override
    public List<Transaction> getAll() {
        conn = dbConnector.startConnect();
        List<Transaction> data = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM trx_ticket");
            ResultSet result = ps.executeQuery();

            while(result.next()) {
                Integer id = result.getInt("id");
                Integer seatId = result.getInt("seat_id");
                Integer customerId = result.getInt("customer_id");

                data.add(new Transaction(id, seatId, customerId));
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
    public void save(Transaction transaction) {
        conn = dbConnector.startConnect();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO trx_ticket " +
                    "(seat_id, customer_id) VALUES (?,?)");
            ps.setInt(1, transaction.getSeat_id());
            ps.setInt(2, transaction.getCustomer_id());
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
    public void update(Transaction transaction) {
        conn = dbConnector.startConnect();
        if (this.getById(transaction.getId()) != null) {
            try {
                PreparedStatement ps = conn.prepareStatement("UPDATE trx_ticket SET seat_id = ?, " +
                        "customer_id = ? WHERE id = ?");
                ps.setInt(1, transaction.getSeat_id());
                ps.setInt(2, transaction.getCustomer_id());
                ps.setInt(3, transaction.getId());
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
                PreparedStatement ps = conn.prepareStatement("DELETE FROM trx_ticket WHERE id = ?");
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
    public Transaction getById(Integer id) {
        conn = dbConnector.startConnect();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM trx_ticket WHERE id = ?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                Integer seatId = result.getInt("seat_id");
                Integer customerId = result.getInt("customer_id");
                return new Transaction(id, seatId, customerId);
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
