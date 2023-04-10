package backend.microservice_compare_cars.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/car/compare/comparison")
public class CompareController {
    @PostMapping
    public ResponseEntity<CarInfo> processDataFromApp8081(@RequestBody CarsInfo carsInfo) {
        // Process the received data model as needed
        return ResponseEntity.ok(carsInfo);
    }
}
