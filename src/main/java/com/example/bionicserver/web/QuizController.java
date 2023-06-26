package com.example.bionicserver.web;

import com.example.bionicserver.data.Car;
import com.example.bionicserver.services.QuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<Car>> loadCarsForQuiz(){
        try{
            if(quizService.isStarted()) quizService.handleQuiz();
            else quizService.startQuiz();
            return ResponseEntity.ok(quizService.selectCarsForQuiz());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PatchMapping
    public ResponseEntity<Void> updateQuiz(@RequestBody Map<String, Integer> body){
        try{
            quizService.saveChoice(body.get("id"));
            return ResponseEntity.ok().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> quitQuiz(){
        try{
            quizService.quitQuiz();
            return ResponseEntity.ok().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
