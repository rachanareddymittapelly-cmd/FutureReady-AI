package com.futureready.backend.controller;

import com.futureready.backend.service.LearningRoadmapService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/learning-roadmap")
@CrossOrigin("*")
public class LearningRoadmapController {

    private final LearningRoadmapService learningRoadmapService;

    public LearningRoadmapController(
            LearningRoadmapService learningRoadmapService) {
        this.learningRoadmapService = learningRoadmapService;
    }

    @GetMapping
    public List<Map<String, Object>> getLearningRoadmap(
            @RequestParam Long studentId,
            @RequestParam Long careerId) {

        return learningRoadmapService.generateRoadmap(
                studentId, careerId);
    }
}