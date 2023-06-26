package com.example.bionicserver.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ParametersWeight implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double yearOfManufacture;
    private double mileage;
    private double price;
    private double horsePower;
    private double typeOfFuel;
    private double gearBox;
}
