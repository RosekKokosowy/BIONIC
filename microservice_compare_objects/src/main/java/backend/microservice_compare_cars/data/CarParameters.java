package backend.microservice_compare_cars.data;

import lombok.Data;

@Data
public class CarParameters {

    private int id;
    private int yearOfManufacture;
    private long price;
    private int horsePower;
    private String typeOfFuel;
    private String gearBox;
    private String wheelDrive;

    public CarParameters(int id, int yearOfManufacture, long price, int horsePower, String typeOfFuel, String gearBox, String wheelDrive) {
        this.id = id;
        this.yearOfManufacture = yearOfManufacture;
        this.price = price;
        this.horsePower = horsePower;
        this.typeOfFuel = typeOfFuel;
        this.gearBox = gearBox;
        this.wheelDrive = wheelDrive;
    }

    public CarParameters() {

    }
}

