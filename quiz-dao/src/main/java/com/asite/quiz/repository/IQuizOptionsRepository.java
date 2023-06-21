package com.asite.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.asite.quiz.entity.QuizOptions;

public interface IQuizOptionsRepository extends JpaRepository<QuizOptions, Long> {

	List<QuizOptions> findByQId(Long QId);
	
	@Modifying
	void updateById(String choice, Long id);
	
	List<Object[]> selectRandomOption(Long id);
}
