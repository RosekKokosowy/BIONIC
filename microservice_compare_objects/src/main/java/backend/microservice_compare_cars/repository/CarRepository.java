package backend.microservice_compare_cars.repository;

import backend.microservice_compare_cars.data.CarParameters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<CarParameters, Long> {

}
