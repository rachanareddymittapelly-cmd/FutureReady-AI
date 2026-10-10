package com.futureready.backend.controller;

import com.futureready.backend.entity.Career;
import com.futureready.backend.service.CareerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/careers")
@CrossOrigin("*")
public class CareerController {

    private final CareerService careerService;

    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @PostMapping
    public Career saveCareer(@RequestBody Career career) {
        return careerService.saveCareer(career);
    }

    @GetMapping
    public List<Career> getAllCareers() {
        return careerService.getAllCareers();
    }
}