package backend.microservice_compare_cars.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Car {

    @Id
    @GeneratedValue
    private int id;
    private int yearOfManufacture;
    private long price;
    private int horsePower;
    private Fuel typeOfFuel;
    private GearBox gearBox;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(int yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public Fuel getTypeOfFuel() {
        return typeOfFuel;
    }

    public void setTypeOfFuel(Fuel typeOfFuel) {
        this.typeOfFuel = typeOfFuel;
    }

    public GearBox getGearBox() {
        return gearBox;
    }

    public void setGearBox(GearBox gearBox) {
        this.gearBox = gearBox;
    }

    public WheelDrive getWheelDrive() {
        return wheelDrive;
    }

    public void setWheelDrive(WheelDrive wheelDrive) {
        this.wheelDrive = wheelDrive;
    }

    private WheelDrive wheelDrive;

}

enum Fuel{
    GASOLINE,
    AVGAS,
    AVTUR,
    KEROSENE,
    SOLAR_OIL,
    DIESEL_OIL,
    FUEL_OIL,
    BIODIESEL
}

enum GearBox{
    MANUAL,
    AUTOMATIC
}

enum WheelDrive{
    RWD,
    FWD,
    AWD
}
