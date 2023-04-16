package backend.microservice_compare_cars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Dictionary;
@Service
public class CarComparatorService {

    private double [] car_1;
    private double [] car_2;
    private double [] weights;

    private double [] car_1_normalized={1};
    private double [] car_2_normalized={1};

    private double  car1_result;
    private double  car2_result;

    //protects from normalization and calculation before initialization
    private boolean init_block = false;
    private boolean normal_block = false;
    private boolean boundaries_block = false;

    private double [] max_value;
    private double [] min_value;


    public Dictionary<String, String> uncountable_params;

    //bob the builder
    //data required by the constructor: (car1 params type: double [], car2 params type: double [], weights type: double [],
    //                                   min values for params type: double [], max values for params type: double [],
    //                                   indexes of inverted parameters type: double [] *-1 if there are none*,
    //                                   car1 wheel drive type: String *available: awd, rwd, fwd*, car2 wheel drive
    //                                   type: String, car1 fuel type: String *available: petrol, diesel, electric,
    //                                   gas, hydrogen. , 3 strings: preferred fuel, wheels and gearbox type: ArrayList<String>)


    public void setObject( double [] a, double [] b, double [] w, double [] min, double [] max, int[] inv)
    {
        //TODO
        //NOWA FUNCKCJA LICZĄCA TYPE OF FUEL I GEARBOX

        car_1 = a;
        car_2 = b;
        weights = w;

        init_block = true;

        set_boundaries(min, max);

        try {
            check_integrity();
        }catch (Exception e) {
            e.printStackTrace();
        }

        swap_boundaries(inv);
    }

    //function returns the prefered car (1 or 2)
    public int PerformAnalisis()
    {
        calculate_preference();

        return car1_result > car2_result ? 1 : 2;
    }

    private void swap_boundaries(int[] inv)
    {
        if(boundaries_block)
        {
            //loop for inverting boundaries (if smaller == better for example mileage)
            if(inv[0] != -1)
            {
                for (int j : inv) {
                     double temp = min_value[j];
                    min_value[j] = max_value[j];
                    max_value[j] = temp;
                }
            }
        }
    }

    //initializes the min and max values for each car trait
    public void set_boundaries( double [] min, double [] max)
    {
        min_value = min;
        max_value = max;

        boundaries_block = true;
    }

    //determines which car has higher preference, performed last
    private void calculate_preference()
    {
        car1_result = 0;
        car2_result = 0;
        for(int i = 0 ; i < car_1_normalized.length ; i++)
        {
            car1_result += car_1_normalized[i] * weights[i];
            car2_result += car_2_normalized[i] * weights[i];
        }
    }

    //normalizes the values to range 0-1
    public void normalize()
    {
        if(init_block && boundaries_block)
        {
            for(int i = 0 ; i < car_1.length ; i++)
            {
                car_1_normalized[i] = car_1[i]/(max_value[i] - min_value[i]);
                car_2_normalized[i] = car_2[i]/(max_value[i] - min_value[i]);
            }

            normal_block = true;
        }
    }

    //makes sure the data given is acceptable
    private void check_integrity() throws Exception {
        int baseline = car_1.length;

        if(car_2.length != baseline || weights.length != baseline)
            throw new Exception("mismatched trait and weight array lengths: \ncar1 " + baseline + "\ncar2 " + car_2.length
                    + "\nweights " + weights.length);

        if(max_value.length != baseline || min_value.length != baseline)
            throw new Exception("mismatched boundaries lengths: \ncar1 " + baseline + "\nmax " + max_value.length
                    + "\nmin " + min_value.length);

        for(int i = 0 ; i < baseline ; i++)
        {
            if(car_1[i] < min_value[i] || car_1[i] > max_value[i])
                throw new Exception("mismatched limits and values:\ncar1: " + car_1[i] + "\nmin: " + min_value[i]
                        + "\nmax: " + max_value[i]);
            if(car_2[i] < min_value[i] || car_2[i] > max_value[i])
                throw new Exception("mismatched limits and values:\ncar2: " + car_2[i] + "\nmin: " + min_value[i]
                        + "\nmax: " + max_value[i]);
        }

        for(int i = 0 ; i < max_value.length ; i++)
        {
            if(max_value[i] == min_value[i])  throw new Exception("max_value = min_value at index: " + i + " Cannot divide by zero!");
        }

        boolean condition = false;

    }
}
