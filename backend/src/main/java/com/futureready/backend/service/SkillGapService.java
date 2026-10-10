package com.futureready.backend.service;

import com.futureready.backend.entity.CareerSkill;
import com.futureready.backend.entity.StudentSkill;
import com.futureready.backend.repository.CareerSkillRepository;
import com.futureready.backend.repository.StudentSkillRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SkillGapService {

    private final CareerSkillRepository careerSkillRepository;
    private final StudentSkillRepository studentSkillRepository;

    public SkillGapService(
            CareerSkillRepository careerSkillRepository,
            StudentSkillRepository studentSkillRepository) {

        this.careerSkillRepository = careerSkillRepository;
        this.studentSkillRepository = studentSkillRepository;
    }

    public List<Map<String, Object>> calculateSkillGaps(
            Long studentId, Long careerId) {

        List<StudentSkill> studentSkills =
                studentSkillRepository.findAll();

        List<CareerSkill> careerSkills =
                careerSkillRepository.findAll();

        Map<Long, Integer> currentLevels = new HashMap<>();

        for (StudentSkill studentSkill : studentSkills) {
            if (studentSkill.getStudent().getId().equals(studentId)) {

                Long skillId = studentSkill.getSkill().getId();

                // Keep the first record for each skill.
                currentLevels.putIfAbsent(
                        skillId, studentSkill.getCurrentLevel());
            }
        }

        List<Map<String, Object>> results = new ArrayList<>();

        for (CareerSkill careerSkill : careerSkills) {

            if (careerSkill.getCareer().getId().equals(careerId)) {

                Long skillId = careerSkill.getSkill().getId();

                int requiredLevel = careerSkill.getRequiredLevel();

                int currentLevel =
                        currentLevels.getOrDefault(skillId, 0);

                int gap = Math.max(
                        requiredLevel - currentLevel, 0);

                Map<String, Object> result = new LinkedHashMap<>();

                result.put("skill", careerSkill.getSkill().getName());
                result.put("currentLevel", currentLevel);
                result.put("requiredLevel", requiredLevel);
                result.put("skillGap", gap);

                results.add(result);
            }
        }

        return results;
    }
}