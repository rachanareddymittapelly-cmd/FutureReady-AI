package com.futureready.backend.controller;

import com.futureready.backend.service.CareerMatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/career-matches")
@CrossOrigin("*")
public class CareerMatchController {

    private final CareerMatchService careerMatchService;

    public CareerMatchController(CareerMatchService careerMatchService) {
        this.careerMatchService = careerMatchService;
    }

    @GetMapping
    public List<Map<String, Object>> getCareerMatches(
            @RequestParam Long studentId) {

        return careerMatchService.calculateCareerMatches(studentId);
    }
}