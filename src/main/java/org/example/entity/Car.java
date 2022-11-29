package org.example.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class Car {
    private String mark;
    private String model;
    private int createdYear;
    private int mileage;
    private double price;
}
