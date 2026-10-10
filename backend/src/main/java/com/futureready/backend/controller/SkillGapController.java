package com.futureready.backend.controller;

import com.futureready.backend.service.SkillGapService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skill-gaps")
@CrossOrigin("*")
public class SkillGapController {

    private final SkillGapService skillGapService;

    public SkillGapController(SkillGapService skillGapService) {
        this.skillGapService = skillGapService;
    }

    @GetMapping
    public List<Map<String, Object>> getSkillGaps(
            @RequestParam Long studentId,
            @RequestParam Long careerId) {

        return skillGapService.calculateSkillGaps(
                studentId, careerId);
    }
}