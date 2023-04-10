package backend.microservice_compare_cars.data;

import lombok.Data;

@Data
public class ParametersWeight {
    private boolean yearOfManufacture;
    private boolean mileage;
    private boolean price;
    private boolean horsePower;
    private boolean typeOfFuel;
    private boolean gearBox;
}
