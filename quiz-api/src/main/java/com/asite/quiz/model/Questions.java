package com.asite.quiz.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Questions {

	private Long id;
	private Long streamId;
	private List<Question> questions;
}
