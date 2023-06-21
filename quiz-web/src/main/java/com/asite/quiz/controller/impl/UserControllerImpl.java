package com.asite.quiz.controller.impl;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.asite.quiz.constants.CommonConstants;
import com.asite.quiz.controller.IUserController;
import com.asite.quiz.service.UserService;

import lombok.val;

@RestController
@CrossOrigin(origins = "*")
public class UserControllerImpl implements IUserController {
	
	@Autowired
	private UserService userService;

	/**
	 * Load Questions for test
	 * 
	 */
	@Override
	public ResponseEntity<?> getTestQuestions(HttpServletRequest request) {
		val map = new HashMap<String, Object>();
		map.put(CommonConstants.FIELD_ID, request.getParameter(CommonConstants.FIELD_ID));
		map.put(CommonConstants.STREAM_ID, request.getParameter(CommonConstants.STREAM_ID));
		try {
			return ResponseEntity.ok(userService.getTestQuestions(map));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Test Question Load Failed " + e);
		}
	}

}
