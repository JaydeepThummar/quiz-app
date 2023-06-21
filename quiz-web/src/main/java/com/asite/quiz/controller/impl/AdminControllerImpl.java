package com.asite.quiz.controller.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.asite.quiz.constants.APIResponseMessage;
import com.asite.quiz.constants.CommonConstants;
import com.asite.quiz.controller.IAdminController;
import com.asite.quiz.model.QuestionData;
import com.asite.quiz.model.Questions;
import com.asite.quiz.service.AdminService;
import com.google.gson.Gson;

import lombok.val;

@RestController
@CrossOrigin(origins = "*")
public class AdminControllerImpl implements IAdminController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminControllerImpl.class);
	
	@Autowired
	private AdminService adminService;
	
	/**
	 * Add question list submitted by Admin
	 * 
	 */
	@Override
	public ResponseEntity<?> addQuestions(HttpServletRequest request) {
		try {
			String json = request.getReader().lines().collect(Collectors.joining());
			Gson gson = new Gson();
			Questions questions = gson.fromJson(json, Questions.class);
			return ResponseEntity.ok(adminService.addQuestions(questions));
		} catch (Exception e) {
			LOGGER.error("Exception in AdminControllerImpl.addQuestions {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	/**
	 * Get Questions list based on given range
	 * 
	 */
	@Override
	public ResponseEntity<?> getQuestionList(HttpServletRequest request) {
		try {
			val map = new HashMap<String, Object>();
			map.put(CommonConstants.FIELD_ID, Long.parseLong(request.getParameter(CommonConstants.FIELD_ID)));
			map.put(CommonConstants.STREAM_ID, Long.parseLong(request.getParameter(CommonConstants.STREAM_ID)));
			map.put(CommonConstants.FROM, Long.parseLong(request.getParameter(CommonConstants.FROM)) - 1);
			map.put(CommonConstants.TO,
					Long.parseLong(request.getParameter(CommonConstants.TO)) 
					- Long.parseLong(request.getParameter(CommonConstants.FROM)) 
					+ 1);
			
			val resultList = adminService.getQuestionList(map);
			if (resultList != null)
				return ResponseEntity.ok(resultList);
			else
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(APIResponseMessage.NO_QUESTIONS_FOUND);
		} catch (Exception e) {
			LOGGER.error("Error in AdminControllerImpl.getQuestionList {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	/**
	 * Get question data based on question id
	 * 
	 */
	@Override
	public ResponseEntity<?> getQuestionData(Long qId) {
		val question = adminService.getQuestionData(qId);
		if (question != null)
			return ResponseEntity.ok(question);
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponseMessage.QUESTION_LOAD_FAILED);
	}

	/**
	 * Update Question data
	 * @throws IOException 
	 */
	@Override
	public ResponseEntity<?> updateQuestionData(HttpServletRequest request) throws IOException {
		String json = request.getReader().lines().collect(Collectors.joining());
		Gson gson = new Gson();
		QuestionData questionData = gson.fromJson(json, QuestionData.class);
		
		val result = adminService.updateQuestionData(questionData);
		if (result != null)
			return ResponseEntity.ok(result);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(APIResponseMessage.UPDATE_QUETION_FAILED);
	}
}
