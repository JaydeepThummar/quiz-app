package com.asite.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OptionData {

	private Long id;
	private String option;
	private int isAnswer;
	
	public OptionData() {
		super();
	}
}
