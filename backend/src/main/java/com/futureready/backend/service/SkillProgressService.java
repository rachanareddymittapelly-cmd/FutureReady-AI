package com.futureready.backend.service;

import com.futureready.backend.entity.SkillProgress;
import com.futureready.backend.repository.SkillProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillProgressService {

    private final SkillProgressRepository skillProgressRepository;

    public SkillProgressService(
            SkillProgressRepository skillProgressRepository) {
        this.skillProgressRepository = skillProgressRepository;
    }

    public SkillProgress saveProgress(SkillProgress progress) {
        return skillProgressRepository.save(progress);
    }

    public List<SkillProgress> getAllProgress() {
        return skillProgressRepository.findAll();
    }

    public List<SkillProgress> getProgressByStudent(Long studentId) {
        return skillProgressRepository.findByStudentId(studentId);
    }

    public List<SkillProgress> getProgressByStudentAndSkill(
            Long studentId, Long skillId) {

        return skillProgressRepository
                .findByStudentIdAndSkillId(studentId, skillId);
    }
}