package com.example.bionicserver.dtos;

import com.example.bionicserver.data.Car;
import com.example.bionicserver.data.ParametersWeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class ComparisonDto {
    private List<Car> cars;
    private ParametersWeight parametersWeight;
}
