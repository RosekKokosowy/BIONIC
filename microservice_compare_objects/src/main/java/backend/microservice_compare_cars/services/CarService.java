package backend.microservice_compare_cars.services;

import backend.microservice_compare_cars.data.CarParameters;
import backend.microservice_compare_cars.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;
    public List<CarParameters> list(){
        CarParameters carParameters = new CarParameters(
                1,
                2018,
                220000,
                220,
                "GASOLINE",
                "AUTOMATIC",
                "AWD"
                );
        carRepository.save(carParameters);
        log.info(carRepository.findAll().toString());
        return carRepository.findAll();
    }
}
