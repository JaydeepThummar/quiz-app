package com.asite.quiz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

import lombok.Getter;
import lombok.Setter;

@NamedNativeQueries(value = {
		  @NamedNativeQuery(name = "QuizOptions.updateById", query = "UPDATE quiz_options SET choices = ?1 where id = ?2"),
		  @NamedNativeQuery(name = "QuizOptions.selectRandomOption", query = "SELECT id,choices from quiz_options where q_id=?1 ORDER BY RAND()")})
@Getter
@Setter
@Entity
public class QuizOptions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String choices;
	
	@Column
	private Long qId;
}
