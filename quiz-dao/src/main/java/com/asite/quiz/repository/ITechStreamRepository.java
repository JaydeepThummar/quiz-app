package com.asite.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asite.quiz.entity.TechEntity;

@Repository
public interface ITechStreamRepository extends JpaRepository<TechEntity, Long> {

	public TechEntity findById(Long streamId);
}
