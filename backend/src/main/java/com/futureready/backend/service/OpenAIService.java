package com.futureready.backend.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OpenAIService {

    public String getCareerAdvice(String studentSkills, String careerGoal) {

        StringBuilder advice = new StringBuilder();

        String goal = careerGoal.toLowerCase();
        String skills = studentSkills.toLowerCase();

        advice.append("FUTUREREADY AI - PERSONALIZED CAREER REPORT\n\n");

        // 1. Career goal
        advice.append("1. CAREER GOAL\n");
        advice.append("Your selected career is: ")
                .append(careerGoal)
                .append("\n\n");

        // 2. Current skills
        advice.append("2. YOUR CURRENT SKILLS\n");
        advice.append(studentSkills).append("\n\n");

        // 3. Skill gap analysis
        advice.append("3. SKILL GAP ANALYSIS\n");

        List<String> requiredSkills = new ArrayList<>();

        if (goal.contains("full stack")) {
            requiredSkills.addAll(
                    Arrays.asList("Java", "React", "SQL", "Spring Boot", "JavaScript", "Git")
            );
        } else if (goal.contains("java")) {
            requiredSkills.addAll(
                    Arrays.asList("Java", "Spring Boot", "SQL", "REST API", "Git")
            );
        } else if (goal.contains("data analyst")) {
            requiredSkills.addAll(
                    Arrays.asList("Python", "SQL", "Excel", "Statistics", "Data Visualization")
            );
        } else if (goal.contains("python")) {
            requiredSkills.addAll(
                    Arrays.asList("Python", "SQL", "Problem Solving", "Git")
            );
        } else {
            requiredSkills.addAll(
                    Arrays.asList("Programming", "Problem Solving", "SQL", "Git")
            );
        }

        List<String> missingSkills = new ArrayList<>();

        for (String skill : requiredSkills) {
            if (!skills.contains(skill.toLowerCase())) {
                missingSkills.add(skill);
            }
        }

        if (missingSkills.isEmpty()) {
            advice.append("Your listed skills cover the main recommended skills. ");
            advice.append("Continue practising and building projects.\n\n");
        } else {
            advice.append("Focus on learning or improving these skills:\n");

            for (String skill : missingSkills) {
                advice.append("- ").append(skill).append("\n");
            }

            advice.append("\n");
        }

        // 4. Personalized 30-day learning plan
        advice.append("4. YOUR 30-DAY LEARNING PLAN\n");

        advice.append("Days 1-5: Revise programming fundamentals and problem solving.\n");
        advice.append("Days 6-10: Practise your main programming language.\n");
        advice.append("Days 11-15: Learn database concepts and SQL.\n");
        advice.append("Days 16-20: Learn the important frameworks and tools for your career.\n");
        advice.append("Days 21-25: Build a small project using your skills.\n");
        advice.append("Days 26-30: Test your project, improve your resume, and practise interviews.\n\n");

        // 5. Project suggestions
        advice.append("5. PROJECT SUGGESTIONS\n");

        if (goal.contains("full stack")) {
            advice.append("- Student Management System\n");
            advice.append("- Job Portal Web Application\n");
            advice.append("- Online Learning Platform\n");
        } else if (goal.contains("java")) {
            advice.append("- Library Management System\n");
            advice.append("- Employee Management System\n");
            advice.append("- Online Banking Simulation\n");
        } else if (goal.contains("data analyst")) {
            advice.append("- Student Performance Analysis\n");
            advice.append("- Sales Data Dashboard\n");
            advice.append("- Customer Data Analysis\n");
        } else {
            advice.append("- Personal Portfolio Website\n");
            advice.append("- Student Management System\n");
            advice.append("- Career Recommendation System\n");
        }

        // 6. Career preparation
        advice.append("\n6. CAREER PREPARATION TIPS\n");
        advice.append("- Practise coding regularly.\n");
        advice.append("- Build projects to demonstrate your skills.\n");
        advice.append("- Upload your projects to GitHub.\n");
        advice.append("- Prepare your resume and practise interview questions.\n");
        advice.append("- Track your progress and update your skill levels regularly.\n");

        return advice.toString();
    }
}