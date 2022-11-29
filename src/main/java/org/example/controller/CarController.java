package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.CarDao;
import org.example.entity.Car;
import org.example.entityEnum.CarMarkEnum;
import org.example.exceptions.NotFoundCarMarkException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CarController {
    @Autowired
    private CarDao carDao;


    @PostMapping(path = "car")
    public int createCar(@RequestBody Map<String, Object> jsonData) throws NotFoundCarMarkException {
        int id = carDao.getCarId();

        carDao.createCar(getCarDataFromJson(jsonData));

        return id;
    }

    @GetMapping(path = "cars")
    public Map<Integer, Car> getCars() {
        return CarDao.getCars();
    }

    @GetMapping(path = "car/{id}")
    public String getCarJsonFromDao(@PathVariable Integer id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(carDao.getCar(id));
    }

    private Car getCarDataFromJson(Map<String, Object> json) throws NotFoundCarMarkException {
        Car car = new Car();

        String mark = (String) json.get("mark");
        String model = (String) json.get("model");
        double price;
        Map<String, Object> historyMap = (Map<String, Object>) json.get("history");
        int mileage = (int) historyMap.get("mileage");
        int createdYear = (int) historyMap.get("created_year");
        int countOwners = (int) historyMap.get("count_owners");

        try {
            price = getBasePriceOnCarMark(mark);
        } catch (NotFoundCarMarkException e) {
            throw new NotFoundCarMarkException();
        }

        price = getPriceOnMileage(mileage, price);
        price = getPriceOnOwnersCount(countOwners, price);

        car.setMark(mark);
        car.setModel(model);
        car.setCreatedYear(createdYear);
        car.setMileage(mileage);
        car.setPrice(price);

        return car;
    }


    private double getBasePriceOnCarMark(String mark) throws NotFoundCarMarkException {
        CarMarkEnum carMark = CarMarkEnum.fromValue(mark);

        return switch (carMark) {
            case BMW -> 50000.0;
            case MERCEDES -> 70000.0;
            case KIA -> 35000.0;
            default -> throw new NotFoundCarMarkException();
        };
    }

    private double getPriceOnMileage(int mileage, double price) {
        double mileageSale = 0.1;
        double sale = (mileageSale * mileage) / 100;
        log.info(String.valueOf(sale));

        return price - sale;
    }

    private double getPriceOnOwnersCount(int ownersCount, double price) {
        return ownersCount > 1 ? price * 0.8 : price;
    }
}
