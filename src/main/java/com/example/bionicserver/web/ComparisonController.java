package com.example.bionicserver.web;

import com.example.bionicserver.data.CarParameters;
import com.example.bionicserver.data.CarsInfo;
import com.example.bionicserver.data.ParametersWeight;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/car")
public class ComparisonController {

    private final CarsInfo carsInfo;
    private final RestTemplate restTemplate;
    private final String microservice_compare_url = "http://localhost:8081/car/compare/comparison";

    @Autowired
    public ComparisonController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.carsInfo = new CarsInfo();
    }

    @PostMapping("/compare")
    public ResponseEntity<Integer> getCars(@RequestBody CarParameters[] carParameters){
        try{
            carsInfo.setCarsParameters(new ArrayList<>());
            carsInfo.getCarsParameters().addAll(Arrays.asList(carParameters));
            carsInfo.getCarsParameters().get(0).setId(0);
            carsInfo.getCarsParameters().get(1).setId(1);

            return sendForComparison();
        }catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/priorities")
    public ResponseEntity<Integer> getPriorities(@RequestBody ParametersWeight parametersWeight){
        try{
            carsInfo.setParametersWeight(parametersWeight);

            return sendForComparison();
        }catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private ResponseEntity<Integer> sendForComparison() {
        if(carsInfo.getCarsParameters() != null && carsInfo.getParametersWeight() != null){
            CarParameters car = restTemplate.postForObject(
                    microservice_compare_url,
                    carsInfo,
                    CarParameters.class
            );
            carsInfo.setCarsParameters(null);
            carsInfo.setParametersWeight(null);
            return ResponseEntity.ok(car.getId());
        }
        else{
            return ResponseEntity.ok().build();
        }
    }
}


/*
* Test purposes:
* {"carsParameters":[{"typeOfFuel":"GAS","gearBox":"AUTOMATIC","yearOfManufacture":"2000","mileage":"1","price":"1","horsePower":"1"},{"typeOfFuel":"GAS","gearBox":"AUTOMATIC","yearOfManufacture":"2000","mileage":"1","price":"1","horsePower":"1"}],"parametersWeight":{"yearOfManufacture":0.1,"mileageWeight":0.7,"priceWeight":0.2,"horsePowerWeight":0.4,"typeOfFuelWeight":0.3,"gearBoxWeight":0.6}}
* */
