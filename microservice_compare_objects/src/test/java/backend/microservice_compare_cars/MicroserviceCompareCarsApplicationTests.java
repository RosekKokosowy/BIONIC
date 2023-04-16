package backend.microservice_compare_cars;

import backend.microservice_compare_cars.data.CarParameters;
import backend.microservice_compare_cars.services.CarComparatorService;
import backend.microservice_compare_cars.services.CarService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class MicroserviceCompareCarsApplicationTests {


    @Autowired
    private CarService carService;
    @Test
    void whenApplicationStarts_thenHibernateCreatesInitialRecords() {
        List<CarParameters> carsParameters = carService.list();
        Assertions.assertEquals(1, carsParameters.size());
    }
}
