package com.example.bionicserver.services;

import com.example.bionicserver.data.ParametersWeight;
import com.example.bionicserver.repositories.CarRepository;
import com.example.bionicserver.repositories.ParametersWeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PrioritiesService {
    
    private final ParametersWeightRepository parametersWeightRepository;

    @Autowired
    public PrioritiesService(ParametersWeightRepository parametersWeightRepository) {
        this.parametersWeightRepository = parametersWeightRepository;
    }


    public ParametersWeight getParams() throws NullPointerException{
        ParametersWeight parametersWeight = parametersWeightRepository.findFirstByOrderByIdDesc();
        if(parametersWeight == null) throw new NullPointerException("Error: no weights in database!");
        return parametersWeight;
    }

    public void saveParams(ParametersWeight parametersWeight) {
        parametersWeightRepository.save(parametersWeight);
    }
}
