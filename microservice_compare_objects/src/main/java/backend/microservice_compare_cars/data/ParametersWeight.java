package backend.microservice_compare_cars.data;

import lombok.Data;

@Data
public class ParametersWeight {
    private double yearOfManufacture;
    private double mileage;
    private double price;
    private double horsePower;
    private double typeOfFuel;
    private double gearBox;
}
