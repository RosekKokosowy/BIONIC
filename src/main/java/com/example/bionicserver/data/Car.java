package com.example.bionicserver.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Car {

    private int id;
    private String name;
    private String img;
    private CarParameters carParameters;
}
