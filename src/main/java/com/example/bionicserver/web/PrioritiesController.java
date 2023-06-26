package com.example.bionicserver.web;

import com.example.bionicserver.data.ParametersWeight;
import com.example.bionicserver.services.PrioritiesService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/priorities")
public class PrioritiesController {

    private final PrioritiesService prioritiesService;

    @Autowired
    public PrioritiesController(PrioritiesService prioritiesService) {
        this.prioritiesService = prioritiesService;
    }

    @GetMapping
    public ResponseEntity<ParametersWeight> getParams(){
        try{
            return ResponseEntity.ok(prioritiesService.getParams());
        }catch(NullPointerException e){
            return ResponseEntity.ok().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> setParams(@RequestBody ParametersWeight parametersWeight){
        try{
            prioritiesService.saveParams(parametersWeight);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
