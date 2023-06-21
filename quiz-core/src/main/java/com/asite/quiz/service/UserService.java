package com.asite.quiz.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asite.quiz.constants.APIResponseMessage;
import com.asite.quiz.constants.CommonConstants;
import com.asite.quiz.model.TestOption;
import com.asite.quiz.model.TestQuestion;

import lombok.SneakyThrows;
import lombok.val;

@Service
public class UserService {
	
	@Autowired
	private DBService dbService;
	
	/**
	 * Load test questions as per batch size of stream
	 * 
	 * @param map
	 * @return {@code List}
	 */
	@SneakyThrows
	public List<TestQuestion> getTestQuestions(Map<String, Object> map) {
		map.put(CommonConstants.BATCH_SIZE, dbService.getBatchSize(map));
		List<Object[]> questionResultList = dbService.selectRandomQuestion(map);
		List<TestQuestion> questionList = new ArrayList<>();
		
		if (questionResultList != null && questionResultList.size() > 0) {
			for (Object[] object : questionResultList) {
				
				val questionId = ((BigInteger) object[0]).longValue();
				val optionResultList = dbService.selectRandomOption(questionId);
				if (optionResultList != null && optionResultList.size() > 0) {
					
					val optionList = new ArrayList<TestOption>();
					for (Object[] optionObject: optionResultList) {
						optionList.add(new TestOption(((BigInteger) optionObject[0]).longValue(), optionObject[1].toString()));
					}
					questionList.add(new TestQuestion(questionId, object[1].toString(), optionList));
				} else
					throw new Exception(APIResponseMessage.TEST_OPTION_LOAD_FAILED);
			}
			return questionList;
		} else
			throw new Exception(APIResponseMessage.TEST_QUESTION_LOAD_FAILED);
	}
}
