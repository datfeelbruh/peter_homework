package org.example.dao;

import org.example.entity.Car;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CarDao {
    private static Integer carId = 1;
    private static Map<Integer, Car> data = new HashMap<>();

    private static int getNextId() {
        return carId++;
    }
    public static Map<Integer, Car> getCars() {
        return data;
    }

    public Car getCar(Integer id) {
        return data.get(id);
    }

    public void createCar(Car car) {
        data.put(getNextId(), car);
    }

    public int getCarId() {
        return carId;
    }
}
