package carsharing.dao;

import carsharing.entity.Car;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {
    Connection conn;

    public CarDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Car> getAllCar() {
        List<Car> cars = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM CAR";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                cars.add(new Car(rs.getInt("id"), rs.getString("name"),
                        rs.getInt("company_id"), rs.getBoolean("rented")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public void addCar(String s, int id) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES ('" + s + "', '" + id + "')";
            stmt.executeUpdate(sql);
            System.out.println("The car was added!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Car getCar(int id) {
        Car car = new Car();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM CAR WHERE ID = " + id;
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            car.setId(rs.getInt("id"));
            car.setName(rs.getString("name"));
            car.setCompany_id(rs.getInt("company_id"));
            car.setRented(rs.getBoolean("rented"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return car;
    }

    @Override
    public void updateCar(Car car) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "UPDATE CAR " +
                    "SET NAME = '" + car.getName() + "', " +
                    "COMPANY_ID = " + car.getCompany_id() + ", " +
                    "RENTED = " + car.isRented() +
                    " WHERE ID = " + car.getId();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCar(Car car) {

    }

    @Override
    public List<Car> getCarsByCompanyId(int id) {
        List<Car> cars = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM CAR WHERE COMPANY_ID = " + id;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                cars.add(new Car(rs.getInt("id"), rs.getString("name"),
                        rs.getInt("company_id"), rs.getBoolean("rented")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cars;
    }
}
