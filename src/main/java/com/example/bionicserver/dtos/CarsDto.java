package com.example.bionicserver.dtos;

import com.example.bionicserver.data.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarsDto {
    private List<Car> carsParameters;
}