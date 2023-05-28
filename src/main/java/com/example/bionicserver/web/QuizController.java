package com.example.bionicserver.web;

import com.example.bionicserver.data.Car;
import com.example.bionicserver.data.ParametersWeight;
import com.example.bionicserver.data.QuizInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/quiz")
public class QuizController {

    private int carsShown;
    private final QuizInfo quizInfo;
    private final RestTemplate restTemplate;
    private final String microservice_selection_url = "http://localhost:8082/quiz/selection";
    private final String microservice_priorities_url = "http://localhost:8083/quiz/priorities";


    @Autowired
    public QuizController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.quizInfo = new QuizInfo();
        this.quizInfo.setCars(new ArrayList<>());
        this.quizInfo.setChoices(new ArrayList<>());
        this.carsShown = 0;
    }

    @GetMapping
    public ResponseEntity<ArrayList<Car>> loadCarsForQuiz(){
        try{
            if(quizInfo.getChoices().size() == QuizInfo.MAX_SIZE){
                quizInfo.getCars().clear();
                quizInfo.getChoices().clear();
                carsShown = 0;
            }

            if(quizInfo.getChoices().size() == 0 && quizInfo.getCars().size() == 0){
                Car[] carsArray = restTemplate.getForObject(microservice_selection_url, Car[].class);
                if (carsArray != null) {
                    quizInfo.getCars().addAll(Arrays.asList(carsArray));
                }
            }

            ArrayList<Car> carsForQuiz = new ArrayList<>(2);
            carsForQuiz.add(quizInfo.getCars().get(carsShown));
            carsForQuiz.add(quizInfo.getCars().get(carsShown + 1));
            carsShown += 2;

            return ResponseEntity.ok(carsForQuiz);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PatchMapping
    public ResponseEntity<Void> updateQuiz(@RequestParam Integer carId){
        try{
            quizInfo.getChoices().add(carId);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/result")
    public ResponseEntity<ParametersWeight> getQuizResult(){
        try{
            if(quizInfo.getChoices().size() != QuizInfo.MAX_SIZE){
                return ResponseEntity.status(403).build();
            }

            ParametersWeight parametersWeight = restTemplate.postForObject(
                    microservice_priorities_url,
                    quizInfo,
                    ParametersWeight.class
            );

            quizInfo.getChoices().clear();
            quizInfo.getCars().clear();
            carsShown = 0;

            return ResponseEntity.ok(parametersWeight);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> quitQuiz(){
        try{
            quizInfo.getChoices().clear();
            quizInfo.getCars().clear();
            carsShown = 0;
            return ResponseEntity.ok().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
