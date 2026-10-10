package com.futureready.backend.controller;

import com.futureready.backend.entity.StudentSkill;
import com.futureready.backend.service.StudentSkillService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student-skills")
@CrossOrigin("*")
public class StudentSkillController {

    private final StudentSkillService studentSkillService;

    public StudentSkillController(StudentSkillService studentSkillService) {
        this.studentSkillService = studentSkillService;
    }

    // Add a new student skill
    @PostMapping
    public StudentSkill saveStudentSkill(
            @RequestBody StudentSkill studentSkill) {
        return studentSkillService.saveStudentSkill(studentSkill);
    }

    // Get all student skills
    @GetMapping
    public List<StudentSkill> getAllStudentSkills() {
        return studentSkillService.getAllStudentSkills();
    }

    // Update an existing student skill
    @PutMapping("/{id}")
    public StudentSkill updateStudentSkill(
            @PathVariable Long id,
            @RequestBody StudentSkill studentSkill) {

        studentSkill.setId(id);

        return studentSkillService.saveStudentSkill(studentSkill);
    }
}