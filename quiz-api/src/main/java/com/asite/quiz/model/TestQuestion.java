package com.asite.quiz.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class TestQuestion {

	private Long id;
	private String question;
	private List<TestOption> options;
}
