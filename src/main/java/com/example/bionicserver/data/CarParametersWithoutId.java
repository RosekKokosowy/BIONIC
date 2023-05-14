package com.example.bionicserver.data;

import lombok.Data;

@Data
public class CarParametersWithoutId {

    private double yearOfManufacture;
    private double mileage;
    private double price;
    private double horsePower;
    private String typeOfFuel;
    private String gearBox;
}
