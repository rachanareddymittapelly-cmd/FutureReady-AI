package com.futureready.backend.repository;

import com.futureready.backend.entity.StudentSkill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentSkillRepository extends JpaRepository<StudentSkill, Long> {
}