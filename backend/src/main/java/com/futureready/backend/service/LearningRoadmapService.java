package com.futureready.backend.service;

import com.futureready.backend.entity.CareerSkill;
import com.futureready.backend.entity.StudentSkill;
import com.futureready.backend.repository.CareerSkillRepository;
import com.futureready.backend.repository.StudentSkillRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LearningRoadmapService {

    private final CareerSkillRepository careerSkillRepository;
    private final StudentSkillRepository studentSkillRepository;

    public LearningRoadmapService(
            CareerSkillRepository careerSkillRepository,
            StudentSkillRepository studentSkillRepository) {

        this.careerSkillRepository = careerSkillRepository;
        this.studentSkillRepository = studentSkillRepository;
    }

    public List<Map<String, Object>> generateRoadmap(
            Long studentId, Long careerId) {

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

        List<Map<String, Object>> roadmap = new ArrayList<>();

        for (CareerSkill careerSkill : careerSkills) {

            if (careerSkill.getCareer().getId().equals(careerId)) {

                Long skillId = careerSkill.getSkill().getId();

                int currentLevel =
                        currentLevels.getOrDefault(skillId, 0);

                int requiredLevel = careerSkill.getRequiredLevel();

                int gap = Math.max(requiredLevel - currentLevel, 0);

                if (gap > 0) {

                    Map<String, Object> task = new LinkedHashMap<>();

                    task.put("skill",
                            careerSkill.getSkill().getName());

                    task.put("currentLevel", currentLevel);

                    task.put("requiredLevel", requiredLevel);

                    task.put("skillGap", gap);

                    if (currentLevel == 0) {
                        task.put("recommendation",
                                "Start learning this skill from the basics.");
                    } else if (gap >= 30) {
                        task.put("recommendation",
                                "Give this skill high priority and practise regularly.");
                    } else {
                        task.put("recommendation",
                                "Improve this skill through projects and practice.");
                    }

                    roadmap.add(task);
                }
            }
        }

        roadmap.sort((a, b) ->
                Integer.compare(
                        (Integer) b.get("skillGap"),
                        (Integer) a.get("skillGap")));

        return roadmap;
    }
}