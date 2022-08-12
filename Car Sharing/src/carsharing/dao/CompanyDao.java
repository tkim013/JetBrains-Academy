package carsharing.dao;

import carsharing.entity.Company;

import java.util.List;

public interface CompanyDao {
    List<Company> getAllCompany();
    void addCompany(String s);
    Company getCompany(int id);
    void updateCompany(Company company);
    void deleteCompany(Company company);
}
