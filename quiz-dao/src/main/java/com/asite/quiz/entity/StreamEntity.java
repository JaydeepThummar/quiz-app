package com.asite.quiz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class StreamEntity {

	@Id
	private Long id;
	
	@Column
	private String name;
}
