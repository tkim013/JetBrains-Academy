package carsharing;

import carsharing.dao.*;
import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.entity.Customer;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:file:./carsharing";

    // Database credentials
    static final String USER = "sa";
    static final String PASS = "";

    public static void main(String[] args) {

        String fileName = "";
        String option;
        Scanner scanner = new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        CompanyDao companyDao;
        CarDao carDao;
        CustomerDao customerDao;

        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-databaseFileName")) {
                    fileName = args[i + 1];
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        try {

            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = fileName.isEmpty() ? DriverManager.getConnection(DB_URL) :
                    DriverManager.getConnection("jdbc:h2:file:./src/carsharing/db/" +
                            fileName);
            conn.setAutoCommit(true);

            //STEP 3: Execute a query

            //create company table if it does not exist
            if (!tableExists(conn, "COMPANY")) {
                System.out.println("Creating COMPANY table in given database...");
                stmt = conn.createStatement();
                String sql = "CREATE TABLE COMPANY (" +
                        "ID INTEGER NOT NULL AUTO_INCREMENT, " +
                        "NAME VARCHAR(255) NOT NULL UNIQUE, " +
                        "PRIMARY KEY ( ID ))";
                stmt.executeUpdate(sql);
                System.out.println("Created COMPANY table in given database...");
            }

            //create car table if it does not exist
            if (!tableExists(conn, "CAR")) {
                System.out.println("Creating CAR table in given database...");
                stmt = conn.createStatement();
                String sql = "CREATE TABLE CAR (" +
                        "ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                        "NAME VARCHAR(255) NOT NULL UNIQUE, " +
                        "COMPANY_ID INTEGER NOT NULL, " +
                        "RENTED BOOLEAN, " +
                        "CONSTRAINT FK_COMPANY FOREIGN KEY (COMPANY_ID) " +
                        "REFERENCES COMPANY(ID))";
                stmt.executeUpdate(sql);
                System.out.println("Created CAR table in given database...");
            }

            //create customer table if it does not exist
            if (!tableExists(conn, "CUSTOMER")) {
                System.out.println("Creating CUSTOMER table in given database...");
                stmt = conn.createStatement();
                String sql = "CREATE TABLE CUSTOMER (" +
                        "ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                        "NAME VARCHAR(255) NOT NULL UNIQUE, " +
                        "RENTED_CAR_ID INTEGER, " +
                        "CONSTRAINT FK_CAR FOREIGN KEY (RENTED_CAR_ID) " +
                        "REFERENCES CAR(ID))";
                stmt.executeUpdate(sql);
                System.out.println("Created CAR table in given database...");
            }

            stmt = conn.createStatement();
            String sql = "ALTER TABLE COMPANY ALTER COLUMN ID RESTART WITH 1";
            stmt.executeUpdate(sql);
            sql = "ALTER TABLE CAR ALTER COLUMN ID RESTART WITH 1";
            stmt.executeUpdate(sql);


            companyDao = new CompanyDaoImpl(conn);
            carDao = new CarDaoImpl(conn);
            customerDao = new CustomerDaoImpl(conn);

            while (true) {

                System.out.println("1. Log in as a manager");
                System.out.println("2. Log in as a customer");
                System.out.println("3. Create a customer");
                System.out.println("0. Exit");

                option = scanner.nextLine();

                if (option.equals("0")) {
                    break;
                }

                switch (option) {
                    case "1": {

                        while (true) {
                            System.out.println("1. Company list");
                            System.out.println("2. Create a company");
                            System.out.println("0. Back");

                            option = scanner.nextLine();

                            if (option.equals("0")) {
                                break;
                            }

                            switch (option) {
                                case "1": {
                                    List<Company> companies = companyDao.getAllCompany();
                                    if (companies.isEmpty()) {
                                        System.out.println("The company list is empty!");
                                    } else {
                                        System.out.println("Choose a company:");
                                        for (Company c : companies) {
                                            System.out.println(c.getId() + ". " + c.getName());
                                        }
                                        System.out.println("0. Back");
                                        option = scanner.nextLine();

                                        if (option.equals("0")) {
                                            break;
                                        }
                                        Company company = companyDao.getCompany(Integer.parseInt(option));
                                        while (true) {
                                            System.out.println(company.getName() + " company:");
                                            System.out.println("1. Car list");
                                            System.out.println("2. Create a car");
                                            System.out.println("0. Back");

                                            option = scanner.nextLine();

                                            if (option.equals("0")) {
                                                break;
                                            }

                                            switch (option) {

                                                case "1": {
                                                    List<Car> cars = carDao.getCarsByCompanyId(company.getId());
                                                    if (cars.isEmpty()) {
                                                        System.out.println("The car list is empty!");
                                                    } else {
                                                        int count = 0;
                                                        for (Car c : cars) {
                                                            System.out.println(++count + ". " + c.getName());
                                                        }
                                                    }
                                                    break;
                                                }

                                                case "2": {
                                                    System.out.println("Enter the car name:");
                                                    String name = scanner.nextLine();
                                                    carDao.addCar(name, company.getId());
                                                    break;
                                                }

                                                default: {
                                                    break;
                                                }
                                            }
                                        }

                                    }
                                    break;
                                }

                                case "2": {
                                    System.out.println("Enter the company name:");
                                    String name = scanner.nextLine();
                                    companyDao.addCompany(name);
                                    break;
                                }

                                default: {
                                    break;
                                }
                            }
                        }
                        break;
                    }

                    case "2": {
                        List<Customer> customers = customerDao.getAllCustomer();
                        if (customers.isEmpty()) {
                            System.out.println("The customer list is empty!");
                        } else {
                            System.out.println("Choose a customer:");
                            for (Customer c : customers) {
                                System.out.println(c.getId() + ". " + c.getName());
                            }
                            System.out.println("0. Back");
                            option = scanner.nextLine();

                            if (option.equals("0")) {
                                break;
                            }

                            Customer customer = customerDao.getCustomer(Integer.parseInt(option));
                            while (true) {
                                System.out.println("1. Rent a car");
                                System.out.println("2. Return a rented car");
                                System.out.println("3. My rented car");
                                System.out.println("0. Back");

                                option = scanner.nextLine();

                                if (option.equals("0")) {
                                    break;
                                }

                                switch (option) {

                                    case "1": {
                                        if (customer.getRented_car_id() > 0) {
                                            System.out.println("You've already rented a car!");
                                            break;
                                        }
                                        List<Company> companies = companyDao.getAllCompany();
                                        if (companies.isEmpty()) {
                                            System.out.println("The company list is empty!");
                                        } else {
                                            System.out.println("Choose a company:");
                                            for (Company c : companies) {
                                                System.out.println(c.getId() + ". " + c.getName());
                                            }
                                            System.out.println("0. Back");
                                            option = scanner.nextLine();

                                            if (option.equals("0")) {
                                                break;
                                            }
                                            Company company = companyDao.getCompany(Integer.parseInt(option));
                                            List<Car> cars = carDao.getCarsByCompanyId(company.getId());
                                            if (cars.isEmpty()) {
                                                System.out.println("No available cars in the " +
                                                        company.getName() + " company");
                                            } else {
                                                int count = 0;
                                                System.out.println("Choose a car:");
                                                for (Car c : cars) {
                                                    if (c.isRented()) {
                                                        continue;
                                                    }
                                                    System.out.println(++count + ". " + c.getName());
                                                }
                                                System.out.println("0. Back");
                                                option = scanner.nextLine();

                                                if (option.equals("0")) {
                                                    break;
                                                }

                                                Car car = carDao.getCar(Integer.parseInt(option));
                                                customer.setRented_car_id(car.getId());
                                                car.setRented(true);
                                                customerDao.updateCustomer(customer);
                                                carDao.updateCar(car);
                                                System.out.println("You rented '" +
                                                        carDao.getCar(customer.getRented_car_id()).getName() + "'");
                                            }
                                        }
                                        break;
                                    }

                                    case "2": {
                                        if (customer.getRented_car_id() == 0) {
                                            System.out.println("You didn't rent a car!");
                                        } else {
                                            customer.setRented_car_id(0);
                                            customerDao.updateCustomer(customer);
                                            System.out.println("You've returned a rented car!");
                                        }
                                        break;
                                    }

                                    case "3": {
                                        if (customer.getRented_car_id() == 0) {
                                            System.out.println("You didn't rent a car!");
                                        } else {
                                            Car car = carDao.getCar(customer.getRented_car_id());
                                            System.out.println("Your rented car:");
                                            System.out.println(car.getName());
                                            System.out.println("Company:");
                                            System.out.println(companyDao.getCompany(car.getCompany_id()).getName());
                                        }
                                        break;
                                    }

                                    default: {
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    }

                    case "3": {
                        System.out.println("Enter the customer name:");
                        String name = scanner.nextLine();
                        customerDao.addCustomer(name);
                        break;
                    }

                    default: {
                        System.out.println("hit default");
                        break;
                    }
                }
            }

            // STEP 4: Clean-up environment

            stmt.close();
            conn.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            } //end finally try
        } //end try
        System.out.println("Goodbye!");
    }

    static boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[] {"TABLE"});

        return resultSet.next();
    }
}
