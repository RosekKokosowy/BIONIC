package com.example.bionicserver.services;


import com.example.bionicserver.data.Car;
import com.example.bionicserver.data.ParametersWeight;
import com.example.bionicserver.dtos.CarsDto;
import com.example.bionicserver.dtos.QuizDto;
import com.example.bionicserver.repositories.ParametersWeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class QuizService {

    private QuizDto quizDto;
    private final ParametersWeightRepository parametersWeightRepository;
    private final RestTemplate restTemplate;
    private final String microservice_selection_url = "http://localhost:8082/quiz/selection";
    private final String microservice_priorities_url = "http://localhost:8083/quiz/priorities";

    @Autowired
    public QuizService(ParametersWeightRepository parametersWeightRepository, RestTemplate restTemplate) {
        this.parametersWeightRepository = parametersWeightRepository;
        this.restTemplate = restTemplate;
    }

    public boolean isStarted() {
        return quizDto != null;
    }

    public void startQuiz() throws NullPointerException{
        quizDto = restTemplate.getForObject(microservice_selection_url, QuizDto.class);
        if(quizDto == null) throw new NullPointerException("Error: no cars for quiz handed!");
    }


    public void handleQuiz() throws NullPointerException{
        if(quizDto.getShowed() == QuizDto.MAX_SIZE){

            ParametersWeight parametersWeight = restTemplate.postForObject(
                    microservice_priorities_url,
                    quizDto,
                    ParametersWeight.class
            );

            if(parametersWeight == null) throw new NullPointerException("Error: no weights available!");

            quizDto.getCars().clear();
            quizDto.getChoices().clear();
            quizDto = null;

            parametersWeightRepository.save(parametersWeight);
        }
        else{
            quizDto.setShowed(quizDto.getShowed()+2);
        }
    }

    public ArrayList<Car> selectCarsForQuiz() throws NullPointerException{
        if(quizDto == null) return null;
        ArrayList<Car> cars = new ArrayList<>();
        cars.add(quizDto.getCars().get(quizDto.getShowed()));
        cars.add(quizDto.getCars().get(quizDto.getShowed()+1));
        return cars;
    }

    public void saveChoice(Integer carId) throws NullPointerException{
        quizDto.getChoices().add(carId);
    }

    public void quitQuiz() {
        quizDto.getCars().clear();
        quizDto.getChoices().clear();
        quizDto = null;
    }
}
