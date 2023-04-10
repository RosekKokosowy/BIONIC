package backend.microservice_compare_cars.web;

import backend.microservice_compare_cars.data.CarParameters;
import backend.microservice_compare_cars.data.ParametersWeight;
import backend.microservice_compare_cars.services.CarComparatorService;
import backend.microservice_compare_cars.services.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/car/compare/comparison")
public class CompareController {
    @Autowired
    CarComparatorService carComparatorService;
    @GetMapping
    public ResponseEntity<Integer> processDataFromApp8081(@RequestBody CarParameters carsInfo1,@RequestBody CarParameters carsInfo2, @RequestBody ParametersWeight parametersWeight) {
        double[] a = {carsInfo1.getYearOfManufacture(),carsInfo1.getPrice(),carsInfo1.getHorsePower()};
        double[] b = {carsInfo2.getYearOfManufacture(),carsInfo2.getPrice(),carsInfo2.getHorsePower()};
        double[]w = {parametersWeight.getYearOfManufacture(),parametersWeight.getPrice(),parametersWeight.getHorsePower()};
        double[] min = {1900,0,0};
        double[] max = {2023,1000000,1500};
        int[] inv = {1};
        carComparatorService.setObject(a,b,w,min,max,inv);
        int res = carComparatorService.PerformAnalisis();
        log.info("result " + res);
        return ResponseEntity.ok(res);
    }
}
