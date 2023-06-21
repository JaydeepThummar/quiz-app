package com.asite.quiz.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asite.quiz.constants.APIConstants;

@RestController
@RequestMapping(APIConstants.ADMIN)
@CrossOrigin(origins = "*")
public interface IAdminController {

	@PostMapping(APIConstants.ADD_QUESTIONS)
	public ResponseEntity<?> addQuestions(HttpServletRequest questions);
	
	@GetMapping(APIConstants.GET_QUESTION_LIST)
	public ResponseEntity<?> getQuestionList(@RequestParam HttpServletRequest request);
		
	@GetMapping(APIConstants.GET_QUESTION_DATA)
	public ResponseEntity<?> getQuestionData(@RequestParam(APIConstants.QUESTION_ID) Long qId);
	
	@PostMapping(APIConstants.UPDATE_QUESTION_DATA)
	public ResponseEntity<?> updateQuestionData(HttpServletRequest request) throws IOException;
}
