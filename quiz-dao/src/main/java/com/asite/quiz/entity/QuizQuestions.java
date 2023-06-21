package com.asite.quiz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@NamedNativeQueries(value = {
		  @NamedNativeQuery(name = "QuizQuestions.updateById", query = "UPDATE quiz_questions Q SET correct_answer = ?1 WHERE id = ?2"),
		  @NamedNativeQuery(name = "QuizQuestions.findQuestionByRange", query = "SELECT id, question FROM quiz_questions WHERE field_id = ?1 AND stream_id = ?2 LIMIT ?3, ?4"),
		  @NamedNativeQuery(name = "QuizQuestions.countByFieldIdAndStreamId", query = "SELECT COUNT(*) FROM quiz_questions WHERE field_id=?1 AND stream_id=?2"),
		  @NamedNativeQuery(name = "QuizQuestions.updateByQuestionId", query= "UPDATE quiz_questions SET question=?1, correct_answer=?2, updated_date=?3 where id=?4"),
		  @NamedNativeQuery(name = "QuizQuestions.selectRandomQuestion", query = "SELECT id,question FROM quiz_questions WHERE field_id=?1 and stream_id=?2 AND is_active=?3 ORDER BY RAND() LIMIT ?4")})
@Getter
@Setter
@Entity
public class QuizQuestions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private Long streamId;
	
	@Column
	private Long fieldId;
	
	@Column
	private String question;
	
	@Column 
	private Long correctAnswer;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
	@Column
	private int isActive;
}
