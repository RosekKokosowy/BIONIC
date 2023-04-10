package backend.microservice_compare_cars;

import backend.microservice_compare_cars.data.Car;
import backend.microservice_compare_cars.services.CarService;
import dev.failsafe.internal.util.Assert;
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
        List<Car> cars = carService.list();
        Assertions.assertEquals(1,cars.size());
    }


}
