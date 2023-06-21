package com.asite.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asite.quiz.entity.NonTechEntity;

@Repository
public interface INonTechStreamRepository extends JpaRepository<NonTechEntity, Long> {

	public NonTechEntity findById(Long streamId);
}
