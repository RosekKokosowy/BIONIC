package backend.microservice_compare_cars.services;

import backend.microservice_compare_cars.data.Car;
import backend.microservice_compare_cars.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;
    public List<Car> list(){
        return carRepository.findAll();
    }
}
