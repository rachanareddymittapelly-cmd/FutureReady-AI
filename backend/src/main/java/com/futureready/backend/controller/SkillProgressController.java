package com.futureready.backend.controller;

import com.futureready.backend.entity.SkillProgress;
import com.futureready.backend.service.SkillProgressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skill-progress")
@CrossOrigin("*")
public class SkillProgressController {

    private final SkillProgressService skillProgressService;

    public SkillProgressController(
            SkillProgressService skillProgressService) {
        this.skillProgressService = skillProgressService;
    }

    @PostMapping
    public SkillProgress saveProgress(
            @RequestBody SkillProgress progress) {
        return skillProgressService.saveProgress(progress);
    }

    @GetMapping
    public List<SkillProgress> getAllProgress() {
        return skillProgressService.getAllProgress();
    }

    @GetMapping("/student/{studentId}")
    public List<SkillProgress> getProgressByStudent(
            @PathVariable Long studentId) {
        return skillProgressService.getProgressByStudent(studentId);
    }

    @GetMapping("/student/{studentId}/skill/{skillId}")
    public List<SkillProgress> getProgressByStudentAndSkill(
            @PathVariable Long studentId,
            @PathVariable Long skillId) {
        return skillProgressService
                .getProgressByStudentAndSkill(studentId, skillId);
    }
}