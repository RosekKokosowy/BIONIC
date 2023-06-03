package com.example.bionicserver.web;

import com.example.bionicserver.dtos.CarsDto;
import com.example.bionicserver.services.ComparisonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/car")
public class ComparisonController {

    private final ComparisonService comparisonService;

    @Autowired
    public ComparisonController(ComparisonService comparisonService) {
        this.comparisonService = comparisonService;
    }

    @PostMapping("/compare")
    public ResponseEntity<Long> getCars(@RequestBody CarsDto carsToCompare){
        try{
            carsToCompare = comparisonService.assignProperties(carsToCompare);
            Long selectedId =  comparisonService.sendForComparison(carsToCompare);
            return ResponseEntity.ok(selectedId);
        }catch (NullPointerException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}


/*
* Test purposes:
* {"carsParameters":[{"typeOfFuel":"GAS","gearBox":"AUTOMATIC","yearOfManufacture":"2000","mileage":"1","price":"1","horsePower":"1"},{"typeOfFuel":"GAS","gearBox":"AUTOMATIC","yearOfManufacture":"2000","mileage":"1","price":"1","horsePower":"1"}],"parametersWeight":{"yearOfManufacture":0.1,"mileageWeight":0.7,"priceWeight":0.2,"horsePowerWeight":0.4,"typeOfFuelWeight":0.3,"gearBoxWeight":0.6}}
* */
