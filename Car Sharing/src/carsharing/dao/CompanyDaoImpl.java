package carsharing.dao;

import carsharing.entity.Company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl implements CompanyDao {
    Connection conn;

    public CompanyDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Company> getAllCompany() {
        List<Company> companies = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM COMPANY";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                companies.add(new Company(rs.getInt("id"), rs.getString("name")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public void addCompany(String s) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO COMPANY (NAME) VALUES ('" + s + "')";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Company getCompany(int id) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM COMPANY " +
                    "WHERE ID = " + id;
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            return new Company(rs.getInt("id"), rs.getString("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateCompany(Company company) {

    }

    @Override
    public void deleteCompany(Company company) {

    }
}
