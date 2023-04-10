package com.company;

import java.util.ArrayList;
import java.util.Dictionary;

public class CarComparator {

    private float[] car_1;
    private float[] car_2;
    private float[] weights;

    private String car_1_fuel;
    private String car_2_fuel;

    private String car_1_wheels;
    private String car_2_wheels;

    private String car_1_gearbox;
    private String car_2_gearbox;

    private float[] car_1_normalized;
    private float[] car_2_normalized;

    private float car1_result;
    private float car2_result;

    //protects from normalization and calculation before initialization
    private boolean init_block = false;
    private boolean normal_block = false;
    private boolean boundaries_block = false;

    private float[] max_value;
    private float[] min_value;

    private int[] inverse;

    public ArrayList<String> fuel_types;
    public ArrayList<String> wheel_types;
    public ArrayList<String> gear_types;

    public Dictionary<String, String> uncountable_params;

    //bob the builder
    //data required by the constructor: (car1 params type: float[], car2 params type: float[], weights type: float[],
    //                                   min values for params type: float[], max values for params type: float[],
    //                                   indexes of inverted parameters type: float[] *-1 if there are none*,
    //                                   car1 wheel drive type: String *available: awd, rwd, fwd*, car2 wheel drive
    //                                   type: String, car1 fuel type: String *available: petrol, diesel, electric,
    //                                   gas, hydrogen. , 3 strings: preferred fuel, wheels and gearbox type: ArrayList<String>)
    public CarComparator( float[] a, float[] b, float[] w, float[] min, float[] max, int[] inv,
                          String a_wheels, String b_wheels, String a_fuel, String b_fuel,
                          String a_gear, String b_gear, ArrayList<String> uncountable_parameters)
    {
        //TODO
        //NOWA FUNCKCJA LICZĄCA TYPE OF FUEL I GEARBOX
        fuel_types.add("diesel");
        fuel_types.add("gas");
        fuel_types.add("petrol");
        fuel_types.add("electric");
        fuel_types.add("hydrogen");

        wheel_types.add("4wd");
        wheel_types.add("fwd");
        wheel_types.add("rwd");

        gear_types.add("manual");
        gear_types.add("automatic");

        uncountable_params.put("fuel", uncountable_parameters.get(0));
        uncountable_params.put("wheels", uncountable_parameters.get(1));
        uncountable_params.put("gearbox", uncountable_parameters.get(2));

        car_1 = a;
        car_2 = b;
        weights = w;

        car_1_wheels = a_wheels.toLowerCase();
        car_2_wheels = b_wheels.toLowerCase();

        car_1_fuel = a_fuel.toLowerCase();
        car_2_fuel = b_fuel.toLowerCase();

        car_1_gearbox = a_gear.toLowerCase();
        car_2_gearbox = b_gear.toLowerCase();

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

        float calculated_value_1 = car1_result / car_1.length;
        float calculated_value_2 = car2_result / car_2.length;

        if(car_1_fuel.compareTo(uncountable_params.get("fuel")) == 0)
            car1_result += calculated_value_1;

        if(car_2_fuel.compareTo(uncountable_params.get("fuel")) == 0)
            car2_result += calculated_value_2;

        if(car_1_wheels.compareTo(uncountable_params.get("wheels")) == 0)
            car1_result += calculated_value_1;

        if(car_2_wheels.compareTo(uncountable_params.get("wheels")) == 0)
            car2_result += calculated_value_2;

        if(car_1_gearbox.compareTo(uncountable_params.get("gearbox")) == 0)
            car1_result += calculated_value_1;

        if(car_2_gearbox.compareTo(uncountable_params.get("gearbox")) == 0)
            car2_result += calculated_value_2;

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
                    float temp = min_value[j];
                    min_value[j] = max_value[j];
                    max_value[j] = temp;
                }
            }
        }
    }

    //initializes the min and max values for each car trait
    public void set_boundaries( float[] min, float[] max)
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
        for(String j : fuel_types)
        {
            if (car_1_fuel.compareTo(j) == 0) {
                condition = true;
                break;
            }
        }
        if(!condition)
            throw new Exception("wrong value in car1 fuel type: " + car_1_fuel);
        condition = false;

        for(String j : fuel_types)
        {
            if (car_2_fuel.compareTo(j) == 0) {
                condition = true;
                break;
            }
        }
        if(!condition)
            throw new Exception("wrong value in car2 fuel type: " + car_2_fuel);
        condition = false;

        for(String j : wheel_types)
        {
            if (car_1_wheels.compareTo(j) == 0) {
                condition = true;
                break;
            }
        }
        if(!condition)
            throw new Exception("wrong value in car1 wheel type: " + car_1_wheels);
        condition = false;

        for(String j : wheel_types)
        {
            if (car_2_wheels.compareTo(j) == 0) {
                condition = true;
                break;
            }
        }
        if(!condition)
            throw new Exception("wrong value in car2 wheel type: " + car_2_wheels);
        condition = false;

        for(String j : gear_types)
        {
            if (car_1_gearbox.compareTo(j) == 0) {
                condition = true;
                break;
            }
        }
        if(!condition)
            throw new Exception("wrong value in car1 gearbox type: " + car_1_gearbox);
        condition = false;

        for(String j : gear_types)
        {
            if (car_2_gearbox.compareTo(j) == 0) {
                condition = true;
                break;
            }
        }
        if(!condition)
            throw new Exception("wrong value in car2 gearbox type: " + car_2_gearbox);
        condition = false;

    }
}
