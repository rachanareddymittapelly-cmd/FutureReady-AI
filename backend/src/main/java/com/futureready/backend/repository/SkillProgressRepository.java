package com.futureready.backend.repository;

import com.futureready.backend.entity.SkillProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillProgressRepository
        extends JpaRepository<SkillProgress, Long> {

    List<SkillProgress> findByStudentId(Long studentId);

    List<SkillProgress> findByStudentIdAndSkillId(
            Long studentId,
            Long skillId
    );
}