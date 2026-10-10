package com.futureready.backend.controller;

import com.futureready.backend.service.OpenAIService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ai")
@CrossOrigin("*")
public class AIController {

    private final OpenAIService openAIService;

    public AIController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/career-advice")
    public Map<String, String> getCareerAdvice(
            @RequestBody Map<String, String> request) {

        String studentSkills = request.get("studentSkills");
        String careerGoal = request.get("careerGoal");

        if (studentSkills == null || studentSkills.isBlank()
                || careerGoal == null || careerGoal.isBlank()) {
            throw new IllegalArgumentException(
                    "Student skills and career goal are required."
            );
        }

        String advice = openAIService.getCareerAdvice(
                studentSkills,
                careerGoal
        );

        return Map.of("advice", advice);
    }
}