package com.asite.quiz.model;

import java.util.List;

import lombok.Getter;

@Getter
public class QuestionData {

	private Long questionId;
	private String question;
	private List<OptionData> options;
	
	QuestionData() {
	}
}
