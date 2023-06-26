package com.example.bionicserver.services;

import com.example.bionicserver.data.Car;
import com.example.bionicserver.data.ParametersWeight;
import com.example.bionicserver.dtos.CarsDto;
import com.example.bionicserver.dtos.ComparisonDto;
import com.example.bionicserver.repositories.CarRepository;
import com.example.bionicserver.repositories.ParametersWeightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ComparisonService {

    private final CarRepository carRepository;
    private final ParametersWeightRepository parametersWeightRepository;
    private final RestTemplate restTemplate;
    private final String microservice_comparison_url = "http://localhost:8081/car/compare/comparison";

    @Autowired
    public ComparisonService(RestTemplate restTemplate, CarRepository carRepository, ParametersWeightRepository parametersWeightRepository) {
        this.restTemplate = restTemplate;
        this.carRepository = carRepository;
        this.parametersWeightRepository = parametersWeightRepository;
    }

    public CarsDto assignProperties(CarsDto carsDto) throws NullPointerException{
        if(carsDto.getCarsParameters() == null){
            throw new NullPointerException("Error: input is incorrect!");
        }

        for(int i = 0; i < carsDto.getCarsParameters().size(); i++){
            carsDto.getCarsParameters().get(i).setId((long) i);
            carsDto.getCarsParameters().get(i).setName("none");
            carsDto.getCarsParameters().get(i).setImg("none");
        }

        return carsDto;
    }

    public Long sendForComparison(CarsDto carsDto) throws NullPointerException{
        if(carsDto.getCarsParameters() == null){
            throw new NullPointerException("Error: input is incorrect!");
        }

        ParametersWeight parametersWeight = parametersWeightRepository.findFirstByOrderByIdDesc();

        ComparisonDto comparisonDto = new ComparisonDto();
        comparisonDto.setCars(carsDto.getCarsParameters());
        comparisonDto.setParametersWeight(parametersWeight);

        Car car = restTemplate.postForObject(
                microservice_comparison_url,
                comparisonDto,
                Car.class
        );

        if(car == null){
            throw new NullPointerException("Error: comparison failed!");
        }

        return car.getId();
    }
}
