package carsharing.dao;

import carsharing.entity.Customer;

import java.util.List;

public interface CustomerDao {
    List<Customer> getAllCustomer();
    void addCustomer(String s);
    Customer getCustomer(int id);
    void updateCustomer(Customer customer);
    void deleteCustomer(Customer customer);
}
