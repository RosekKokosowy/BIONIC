package backend.microservice_compare_cars.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class CarParameters implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
