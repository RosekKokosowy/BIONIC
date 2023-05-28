package com.example.bionicserver.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class QuizInfo {
    public static final int MAX_SIZE = 10;
    private ArrayList<Car> cars;
    private ArrayList<Integer> choices;
}
