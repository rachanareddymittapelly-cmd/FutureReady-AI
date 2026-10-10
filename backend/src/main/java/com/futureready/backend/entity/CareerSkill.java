package com.futureready.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "career_skills")
public class CareerSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer requiredLevel;

    @ManyToOne
    @JoinColumn(name = "career_id")
    private Career career;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    public CareerSkill() {
    }

    public CareerSkill(Integer requiredLevel, Career career, Skill skill) {
        this.requiredLevel = requiredLevel;
        this.career = career;
        this.skill = skill;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(Integer requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}