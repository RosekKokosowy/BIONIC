package backend.microservice_compare_cars;

import backend.microservice_compare_cars.data.CarParameters;
import backend.microservice_compare_cars.data.CarsInfo;
import backend.microservice_compare_cars.data.ParametersWeight;
import backend.microservice_compare_cars.services.CarComparatorService;
import backend.microservice_compare_cars.web.CompareController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
class MicroserviceCompareCarsApplicationTests {


    @Test
    public void testCarParametersConstructor() {
        CarParameters carParameters = new CarParameters(1, 2022, 100000, 300000, 300, "GASOLINE", "AUTOMATIC");
        assertEquals(1, carParameters.getId());
        assertEquals(2022, carParameters.getYearOfManufacture());
        assertEquals(300000, carParameters.getPrice());
        assertEquals(300, carParameters.getHorsePower());
        assertEquals("GASOLINE", carParameters.getTypeOfFuel());
        assertEquals("AUTOMATIC", carParameters.getGearBox());
    }

    @Test
    public void testCarParametersDefaultConstructor() {
        CarParameters carParameters = new CarParameters();
        Assertions.assertNotNull(carParameters);
    }

    @Test
    public void testSetAndGetCarsParameters() {
        CarsInfo carsInfo = new CarsInfo();
        ArrayList<CarParameters> carsParameters = new ArrayList<>();
        CarParameters car1 = new CarParameters(1, 2022, 10000, 30000, 300, "MANUAL", "FWD");
        CarParameters car2 = new CarParameters(2, 2021, 15000, 40000, 250, "AUTOMATIC", "RWD");
        carsParameters.add(car1);
        carsParameters.add(car2);
        carsInfo.setCarsParameters(carsParameters);
        assertEquals(carsParameters, carsInfo.getCarsParameters());
    }

    @Test
    public void testSetAndGetParametersWeight() {
        CarsInfo carsInfo = new CarsInfo();
        ParametersWeight parametersWeight = new ParametersWeight(1, 1, 1, 1, 1, 1);
        carsInfo.setParametersWeight(parametersWeight);
        assertEquals(parametersWeight, carsInfo.getParametersWeight());
    }

    @Test
    void testPerformAnalisis() {

        CarComparatorService carComparatorService = new CarComparatorService();
        CarsInfo carsInfo = new CarsInfo();

        carsInfo.setParametersWeight(new ParametersWeight(0.25, 0.25, 0.25, 0.25, 0.25, 0.25));

        CarParameters car1 = new CarParameters(1, 2022, 10000, 30000, 300, "MANUAL", "FWD");
        CarParameters car2 = new CarParameters(2, 2021, 15000, 40000, 250, "AUTOMATIC", "RWD");
        ArrayList<CarParameters> carsParameters = new ArrayList<>();
        carsParameters.add(car1);
        carsParameters.add(car2);
        carsInfo.setCarsParameters(carsParameters);
        carComparatorService.setObject(carsInfo);
        assertEquals(1, carComparatorService.PerformAnalisis());
    }

    @Mock
    private CarComparatorService carComparatorService;

    @InjectMocks
    private CompareController compareController;

    @Test
    public void testEndpointCompareController() {
        CarsInfo carsInfo = new CarsInfo();
        CarParameters carParameters1 = new CarParameters(1, 2022, 10000, 30000, 300, "MANUAL", "FWD");

        ArrayList<CarParameters> carsParameters = new ArrayList<>();
        carsParameters.add(carParameters1);
        carsInfo.setCarsParameters(carsParameters);
        carComparatorService.setObject(carsInfo);

        when(carComparatorService.PerformAnalisis()).thenReturn(0);
        ResponseEntity<CarParameters> response = compareController.processData(carsInfo);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

}
