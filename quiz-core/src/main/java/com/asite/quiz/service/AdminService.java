package com.asite.quiz.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asite.quiz.constants.APIResponseMessage;
import com.asite.quiz.constants.CommonConstants;
import com.asite.quiz.entity.QuizOptions;
import com.asite.quiz.entity.QuizQuestions;
import com.asite.quiz.model.Option;
import com.asite.quiz.model.OptionData;
import com.asite.quiz.model.Question;
import com.asite.quiz.model.QuestionData;
import com.asite.quiz.model.QuestionListModel;
import com.asite.quiz.model.Questions;
import com.asite.quiz.repository.IQuizOptionsRepository;
import com.asite.quiz.repository.IQuizQuestionsRepository;

import lombok.SneakyThrows;
import lombok.val;
import lombok.var;

@Service
public class AdminService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);
	
	@Autowired
	private IQuizQuestionsRepository questionRepository;
	
	@Autowired
	private DBService dbService;
	
	@Autowired
	private IQuizOptionsRepository optionRepository;

	/**
	 * Add questions list to DB
	 * 
	 * @param questions
	 * @return {@code String}
	 */
	@SneakyThrows
	public String addQuestions(Questions questions) {
		try {
			if (questions.getQuestions().size() > 0) {
				for (Question question : questions.getQuestions()) {
					var correctOptionId = 0L;
					var qQ = new QuizQuestions();
					qQ.setQuestion(question.getQuestion());
					qQ.setCreatedDate(new Date());
					qQ.setUpdatedDate(new Date());
					qQ.setIsActive(1);
					qQ.setFieldId(questions.getId());
					qQ.setStreamId(questions.getStreamId());
					qQ.setCorrectAnswer(correctOptionId);
					var savedQQ = questionRepository.save(qQ);
					
					for (Option option : question.getOptions()) {
						var qO = new QuizOptions();
						qO.setChoices(option.getOption());
						qO.setQId(savedQQ.getId());
						var optionId = optionRepository.save(qO).getId();
						if (option.getIsAnswer() == 1 )
							correctOptionId = optionId;
					}
					dbService.updateById(correctOptionId, savedQQ.getId());
				}
				return CommonConstants.SUCCESS;
			} else {
				throw new Exception(APIResponseMessage.NO_QUESTIONS_FOUND);
			}
		} catch (Exception e) {
			throw new Exception(APIResponseMessage.ERROR_IN_STORING_QUESTIONS);
		}
	}
	
	/**
	 * Returns list of questions as per given stream
	 * 
	 * @param map
	 * @return {@code List}
	 */
	@SneakyThrows
	public Map<String, Object> getQuestionList(Map<String, Object> map) {
		try {
			val resulList = dbService.findQuestionByRange(map);
			if (resulList != null && resulList.size() > 0) {
				val questionList = new ArrayList<QuestionListModel>();
				for (Object[] object: resulList) {
					questionList.add(new QuestionListModel(((BigInteger) object[0]).longValue(), (String) object[1]));
				}
				val resultMap = new HashMap<String, Object>();
				resultMap.put(CommonConstants.QUESTIONS, questionList);
				resultMap.put(CommonConstants.COUNT, dbService.countByFieldIdAndStreamId(map));
				return resultMap;
			} else
				return null;
		} catch (Exception e) {
			LOGGER.error("Exception in AdminService.getQuestionList {}", e);
			throw new Exception("Error while getQuestionList");
		}
	}
	
	/**
	 * Retrieve data from DB and fill into {@code Map}
	 * 
	 * @param id
	 * @return {@code Question}
	 */
	public Map<String, Object> getQuestionData(Long id) {
		val questions = questionRepository.findByIdAndIsActive(id, 1);
		QuizQuestions question = null;
		if (questions != null && questions.size() > 0)
			question = questions.get(0);
		if (question != null) {
			
			val resultMap = new HashMap<String, Object>();
			val options = optionRepository.findByQId(question.getId());
			if (options != null && options.size() > 0) {
				
				val o = new ArrayList<OptionData>();
				for (int i = 0; i < options.size(); i++) {
					
					if (options.get(i).getId() == question.getCorrectAnswer())
						o.add(new OptionData(options.get(i).getId(), options.get(i).getChoices(), 1));
					else
						o.add(new OptionData(options.get(i).getId(), options.get(i).getChoices(), 0));
				}
				resultMap.put(CommonConstants.QUESTION_ID, question.getId());
				resultMap.put(CommonConstants.QUESTION, question.getQuestion());
				resultMap.put(CommonConstants.OPTIONS, o);
				resultMap.put(CommonConstants.IS_ACTIVE, question.getIsActive());
				return resultMap;
			}
		}
		return null;
	}
	
	/**
	 * Updates options data and question data as per request
	 * 
	 * @param request
	 * @return {@code String}
	 */
	public String updateQuestionData(QuestionData request) {
		List<OptionData> optionDataList = request.getOptions();
		if (optionDataList != null && optionDataList.size() > 0) {
			var correctAnswer = 0L;
			for (OptionData optionData : optionDataList) {
				dbService.updateById(optionData.getOption(), optionData.getId());
				if (optionData.getIsAnswer() == 1)
					correctAnswer = optionData.getId();
			}
			dbService.updateByQuestionId(request.getQuestion(), correctAnswer, new Date(), request.getQuestionId());
			return CommonConstants.SUCCESS;
		}
		return null;
	}
}
