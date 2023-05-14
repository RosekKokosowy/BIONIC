package com.example.bionicserver.data;

import lombok.Data;

import java.lang.reflect.Field;

@Data
public class ParametersWeight {
    private double yearOfManufacture;
    private double mileage;
    private double price;
    private double horsePower;
    private double typeOfFuel;
    private double gearBox;

    public int getNumParams()
    {
        int res = 0;
        for(Field f : ParametersWeight.class.getDeclaredFields())
        {
            res++;
        }
        return res;
    }
}
