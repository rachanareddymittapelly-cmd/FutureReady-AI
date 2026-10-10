package com.futureready.backend.service;

import com.futureready.backend.entity.CareerSkill;
import com.futureready.backend.entity.StudentSkill;
import com.futureready.backend.repository.CareerSkillRepository;
import com.futureready.backend.repository.StudentSkillRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CareerMatchService {

    private final CareerSkillRepository careerSkillRepository;
    private final StudentSkillRepository studentSkillRepository;

    public CareerMatchService(
            CareerSkillRepository careerSkillRepository,
            StudentSkillRepository studentSkillRepository) {

        this.careerSkillRepository = careerSkillRepository;
        this.studentSkillRepository = studentSkillRepository;
    }

    public List<Map<String, Object>> calculateCareerMatches(Long studentId) {

        List<StudentSkill> studentSkills =
                studentSkillRepository.findAll();

        List<CareerSkill> careerSkills =
                careerSkillRepository.findAll();

        Map<Long, Integer> currentLevels = new HashMap<>();

        for (StudentSkill studentSkill : studentSkills) {

            if (studentSkill.getStudent().getId().equals(studentId)) {

                Long skillId = studentSkill.getSkill().getId();

                currentLevels.putIfAbsent(
                        skillId, studentSkill.getCurrentLevel());
            }
        }

        Map<Long, List<CareerSkill>> careerMap = new LinkedHashMap<>();

        for (CareerSkill careerSkill : careerSkills) {

            Long careerId = careerSkill.getCareer().getId();

            careerMap.computeIfAbsent(
                    careerId, k -> new ArrayList<>()).add(careerSkill);
        }

        List<Map<String, Object>> results = new ArrayList<>();

        for (List<CareerSkill> requirements : careerMap.values()) {

            if (requirements.isEmpty()) {
                continue;
            }

            int currentTotal = 0;
            int requiredTotal = 0;

            for (CareerSkill requirement : requirements) {

                Long skillId = requirement.getSkill().getId();

                int currentLevel =
                        currentLevels.getOrDefault(skillId, 0);

                int requiredLevel = requirement.getRequiredLevel();

                currentTotal += Math.min(currentLevel, requiredLevel);
                requiredTotal += requiredLevel;
            }

            double matchPercentage = requiredTotal == 0
                    ? 0
                    : (currentTotal * 100.0) / requiredTotal;

            CareerSkill firstRequirement = requirements.get(0);

            Map<String, Object> result = new LinkedHashMap<>();

            result.put("careerId",
                    firstRequirement.getCareer().getId());

            result.put("careerName",
                    firstRequirement.getCareer().getName());

            result.put("matchPercentage",
                    Math.round(matchPercentage * 100.0) / 100.0);

            results.add(result);
        }

        results.sort((a, b) ->
                Double.compare(
                        ((Number) b.get("matchPercentage")).doubleValue(),
                        ((Number) a.get("matchPercentage")).doubleValue()));

        return results;
    }
}