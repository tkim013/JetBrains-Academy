package carsharing.dao;

import carsharing.entity.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    Connection conn;

    public CustomerDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Customer> getAllCustomer() {
        List<Customer> customers = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM CUSTOMER";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                customers.add(new Customer(rs.getInt("id"), rs.getString("name"),
                        rs.getInt("rented_car_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public void addCustomer(String s) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO CUSTOMER (NAME) VALUES ('" + s + "')";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("input error: name invalid format");
            e.printStackTrace();
        }
    }

    @Override
    public Customer getCustomer(int id) {
        Customer customer = new Customer();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM CUSTOMER WHERE ID = " + id;
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            customer.setId(rs.getInt("id"));
            customer.setName(rs.getString("name"));
            customer.setRented_car_id(rs.getInt("rented_car_id"));
        } catch (Exception e) {
            System.out.println("error: getCustomer(id)");
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public void updateCustomer(Customer customer) {
        try {
            Statement stmt = conn.createStatement();
            String nullable = customer.getRented_car_id() == 0 ? "NULL" : String.valueOf(customer.getRented_car_id());
            String sql = "UPDATE CUSTOMER " +
                    "SET NAME = '" + customer.getName() + "', " +
                    "RENTED_CAR_ID = " + nullable +
                    " WHERE ID = " + customer.getId();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCustomer(Customer customer) {

    }
}
