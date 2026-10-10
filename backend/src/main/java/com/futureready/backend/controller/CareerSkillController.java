package com.futureready.backend.controller;

import com.futureready.backend.entity.CareerSkill;
import com.futureready.backend.service.CareerSkillService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/career-skills")
@CrossOrigin("*")
public class CareerSkillController {

    private final CareerSkillService careerSkillService;

    public CareerSkillController(CareerSkillService careerSkillService) {
        this.careerSkillService = careerSkillService;
    }

    @PostMapping
    public CareerSkill saveCareerSkill(@RequestBody CareerSkill careerSkill) {
        return careerSkillService.saveCareerSkill(careerSkill);
    }

    @GetMapping
    public List<CareerSkill> getAllCareerSkills() {
        return careerSkillService.getAllCareerSkills();
    }
}