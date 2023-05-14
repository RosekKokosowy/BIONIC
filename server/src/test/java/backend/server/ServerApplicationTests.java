package backend.server;

import backend.server.config.CorsConfig;
import backend.server.data.CarParameters;
import backend.server.data.CarsInfo;
import backend.server.data.ParametersWeight;
import backend.server.web.ComparisonController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class ServerApplicationTests {

    @Test
    void contextLoads() {
    }

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


    @Mock
    RestTemplate restTemplate;

    ComparisonController comparisonController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        comparisonController = new ComparisonController(restTemplate);
    }

    @Test
    void testCompareCarsNullPointer() {
        CarsInfo carsInfo = new CarsInfo();
        when(restTemplate.postForObject(anyString(), any(CarsInfo.class), eq(CarParameters.class)))
                .thenThrow(NullPointerException.class);

        ResponseEntity<Integer> response = comparisonController.compareCars(carsInfo);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCompareCarsSuccess() {
        CarsInfo carsInfo = new CarsInfo();
        ArrayList<CarParameters> carParametersList = new ArrayList<>();
        CarParameters car1 = new CarParameters(0, 2022, 10000, 30000, 300, "MANUAL", "FWD");
        CarParameters car2 = new CarParameters(1, 2021, 15000, 40000, 250, "AUTOMATIC", "RWD");
        carParametersList.add(car1);
        carParametersList.add(car2);
        carsInfo.setCarsParameters(carParametersList);

        CarParameters expectedResult = new CarParameters(1, 2022, 10000, 30000, 300, "MANUAL", "FWD");
        when(restTemplate.postForObject(anyString(), any(CarsInfo.class), eq(CarParameters.class)))
                .thenReturn(expectedResult);

        ResponseEntity<Integer> response = comparisonController.compareCars(carsInfo);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody());
    }
}
