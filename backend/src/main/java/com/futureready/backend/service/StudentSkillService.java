package com.futureready.backend.service;

import com.futureready.backend.entity.StudentSkill;
import com.futureready.backend.entity.SkillProgress;
import com.futureready.backend.repository.StudentSkillRepository;
import com.futureready.backend.repository.SkillProgressRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentSkillService {

    private final StudentSkillRepository studentSkillRepository;
    private final SkillProgressRepository skillProgressRepository;

    public StudentSkillService(
            StudentSkillRepository studentSkillRepository,
            SkillProgressRepository skillProgressRepository) {

        this.studentSkillRepository = studentSkillRepository;
        this.skillProgressRepository = skillProgressRepository;
    }

    @Transactional
    public StudentSkill saveStudentSkill(StudentSkill studentSkill) {

        // Find the existing skill record before updating
        Integer previousLevel = null;

        if (studentSkill.getId() != null) {
            StudentSkill existingSkill =
                    studentSkillRepository.findById(studentSkill.getId())
                            .orElse(null);

            if (existingSkill != null) {
                previousLevel = existingSkill.getCurrentLevel();
            }
        }

        // Save the skill record
        StudentSkill savedSkill =
                studentSkillRepository.save(studentSkill);

        // Record progress only when an existing skill level changes
        if (previousLevel != null
                && !previousLevel.equals(savedSkill.getCurrentLevel())) {

            SkillProgress progress = new SkillProgress();

            progress.setStudent(savedSkill.getStudent());
            progress.setSkill(savedSkill.getSkill());
            progress.setPreviousLevel(previousLevel);
            progress.setNewLevel(savedSkill.getCurrentLevel());

            skillProgressRepository.save(progress);
        }

        return savedSkill;
    }

    public List<StudentSkill> getAllStudentSkills() {
        return studentSkillRepository.findAll();
    }
}