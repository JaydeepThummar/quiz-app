package com.asite.quiz.repository;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.asite.quiz.entity.QuizQuestions;
import com.asite.quiz.model.QuestionListModel;

@Repository
public interface IQuizQuestionsRepository extends JpaRepository<QuizQuestions, Long> {

	@Modifying
	void updateById(Long correct, Long id);
	
	List<Object[]> findQuestionByRange(Long fieldId, Long streamId, Long from, Long to);
	
	List<QuizQuestions> findByIdAndIsActive(Long id, int isActive);
	
	List<Object[]> countByFieldIdAndStreamId(Long fieldId, Long streamId);
	
	@Modifying
	void updateByQuestionId(String question, Long correctAnswer, Date updatedDate, Long id);
	
	List<Object[]> selectRandomQuestion(Long fieldId, Long streamId, int isActive, int limit);
}
