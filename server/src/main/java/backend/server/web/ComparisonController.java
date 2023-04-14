package backend.server.web;

import backend.server.data.CarParameters;
import backend.server.data.CarsInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/car")
public class ComparisonController {

    private final RestTemplate restTemplate;
    private final String microservice_url = "http://localhost:8081/car/compare/comparison";

    @Autowired
    public ComparisonController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/compare")
    public ResponseEntity<Integer> compareCars(@RequestBody CarsInfo carsInfo){
        try{
            log.info("POST");
            log.info(String.valueOf(carsInfo));
            return ResponseEntity.ok(carsInfo.getCarsParameters().get(0).getId());
            /* Poniższy kod odkomentować i usunac return powyzej, kiedy zostana zmienione klasy POJO w mikroserwisie !
            CarParameters car = restTemplate.postForObject(
                    microservice_url,
                    carsInfo,
                    CarParameters.class
            );
            log.info(String.valueOf(car));
            return ResponseEntity.ok(car.getId());
            */
        }catch (NullPointerException e){
            e.printStackTrace();
            log.info("ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

/*
* Test purposes:
* {"carsParameters":[{"typeOfFuel":"GAS","gearBox":"AUTOMATIC","yearOfManufacture":"2000","mileage":"1","price":"1","horsePower":"1"},{"typeOfFuel":"GAS","gearBox":"AUTOMATIC","yearOfManufacture":"2000","mileage":"1","price":"1","horsePower":"1"}],"parametersWeight":{"yearOfManufacture":0.1,"mileageWeight":0.7,"priceWeight":0.2,"horsePowerWeight":0.4,"typeOfFuelWeight":0.3,"gearBoxWeight":0.6}}
* */
