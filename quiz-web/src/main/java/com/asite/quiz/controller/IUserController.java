package com.asite.quiz.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asite.quiz.constants.APIConstants;

@RestController
@RequestMapping(APIConstants.USER)
@CrossOrigin(origins = "*")
public interface IUserController {

	@GetMapping(APIConstants.GET_TEST_QUESTIONS)
	public ResponseEntity<?> getTestQuestions(HttpServletRequest request);
}
