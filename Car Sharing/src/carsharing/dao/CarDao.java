package carsharing.dao;

import carsharing.entity.Car;

import java.util.List;

public interface CarDao {

    List<Car> getAllCar();
    void addCar(String s, int id);
    Car getCar(int id);
    void updateCar(Car car);
    void deleteCar(Car car);
    List<Car> getCarsByCompanyId(int id);
}
