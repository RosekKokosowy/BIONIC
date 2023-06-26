package com.example.bionicserver.dtos;

import com.example.bionicserver.data.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private ArrayList<Car> cars;
    private ArrayList<Integer> choices;
    private int showed = 0;
    public static final int MAX_SIZE = 36;
}
