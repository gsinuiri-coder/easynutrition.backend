package com.tchelper.EasyNutrition.domain.repository;

import com.tchelper.EasyNutrition.domain.model.Diet;
import com.tchelper.EasyNutrition.domain.model.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DietRepository extends JpaRepository<Diet, Long>{
    Page<Diet> findBySessionId(Long sessionId, Pageable pageable);
    Optional<Diet> findByIdAndSessionId(Long id, Long sessionId);
}