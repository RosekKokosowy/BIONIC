package com.example.bionicserver.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarParameters {

    private int id;
    private String yearOfManufacture;
    private String mileage;
    private String price;
    private String horsePower;
    private String typeOfFuel;
    private String gearBox;
}
