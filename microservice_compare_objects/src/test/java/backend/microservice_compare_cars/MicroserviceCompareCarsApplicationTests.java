package backend.microservice_compare_cars;

import backend.microservice_compare_cars.data.CarParameters;
import backend.microservice_compare_cars.services.CarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MicroserviceCompareCarsApplicationTests {

    @Autowired
    private CarService carService;
    @Test
    void whenApplicationStarts_thenHibernateCreatesInitialRecords() {
        List<CarParameters> carsParameters = carService.list();
        Assertions.assertEquals(1, carsParameters.size());
    }


}
