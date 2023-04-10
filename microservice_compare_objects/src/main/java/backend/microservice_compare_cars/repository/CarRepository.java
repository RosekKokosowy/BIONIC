package backend.microservice_compare_cars.repository;

import backend.microservice_compare_cars.data.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

}
