package com.example.bionicserver.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CarParameters {

    private int id;
    private double yearOfManufacture;
    private double mileage;
    private double price;
    private double horsePower;
    private String typeOfFuel;
    private String gearBox;

    public CarParameters(int id, double yearOfManufacture, double mileage, double price, double horsePower, String typeOfFuel, String gearBox, String wheelDrive) {
        this.id = id;
        this.yearOfManufacture = yearOfManufacture;
        this.mileage = mileage;
        this.price = price;
        this.horsePower = horsePower;
        this.typeOfFuel = typeOfFuel;
        this.gearBox = gearBox;
    }

    @JsonCreator
    public CarParameters(@JsonProperty("typeOfFuel") String typeOfFuel,
                         @JsonProperty("gearBox") String gearBox,
                         @JsonProperty("yearOfManufacture") int yearOfManufacture,
                         @JsonProperty("mileage") int mileage,
                         @JsonProperty("price") int price,
                         @JsonProperty("horsePower") int horsePower) {
        this.typeOfFuel = typeOfFuel;
        this.gearBox = gearBox;
        this.yearOfManufacture = yearOfManufacture;
        this.mileage = mileage;
        this.price = price;
        this.horsePower = horsePower;
    }
}
