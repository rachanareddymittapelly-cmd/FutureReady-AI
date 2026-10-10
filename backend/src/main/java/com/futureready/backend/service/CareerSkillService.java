package com.futureready.backend.service;

import com.futureready.backend.entity.CareerSkill;
import com.futureready.backend.repository.CareerSkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerSkillService {

    private final CareerSkillRepository careerSkillRepository;

    public CareerSkillService(CareerSkillRepository careerSkillRepository) {
        this.careerSkillRepository = careerSkillRepository;
    }

    public CareerSkill saveCareerSkill(CareerSkill careerSkill) {
        return careerSkillRepository.save(careerSkill);
    }

    public List<CareerSkill> getAllCareerSkills() {
        return careerSkillRepository.findAll();
    }
}