package backend.server.web;

import backend.server.data.CarParameters;
import backend.server.data.CarsInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<CarParameters> compareCars(@RequestBody CarsInfo carsInfo){
        CarParameters car = restTemplate.postForObject(
                microservice_url,
                carsInfo,
                CarParameters.class
        );
        log.info(String.valueOf(car));
        return ResponseEntity.ok(car);
    }
}
