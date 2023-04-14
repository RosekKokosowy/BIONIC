package backend.server.data;

import lombok.Data;

import java.util.ArrayList;

@Data
public class CarsInfo {
    long id;
    ParametersWeight parametersWeight;
    ArrayList<CarParameters> cars;
}
