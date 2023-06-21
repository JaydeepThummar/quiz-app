package com.asite.quiz.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asite.quiz.constants.CommonConstants;
import com.asite.quiz.entity.QuizOptions;
import com.asite.quiz.entity.QuizQuestions;
import com.asite.quiz.entity.StreamEntity;
import com.asite.quiz.model.QuestionListModel;
import com.asite.quiz.repository.INonTechStreamRepository;
import com.asite.quiz.repository.IQuizOptionsRepository;
import com.asite.quiz.repository.IQuizQuestionsRepository;
import com.asite.quiz.repository.IStreamRepository;
import com.asite.quiz.repository.ITechStreamRepository;

import lombok.val;
import lombok.var;

@Service
public class DBService {

	@Autowired
	private IQuizQuestionsRepository repository;
	
	@Autowired
	private IQuizOptionsRepository optionRepository;
	
	@Autowired
	private ITechStreamRepository techStreamRepo;
	
	@Autowired
	private INonTechStreamRepository nonTechRepo;
	
	@Autowired
	private IStreamRepository streamRepo;
	
	@Transactional
	public void updateById(Long correct, Long id) {
		repository.updateById(correct, id);
	}
	
	@Transactional
	public List<Object[]> findQuestionByRange(Map<String, Object> map) {
		return repository.findQuestionByRange((Long) map.get(CommonConstants.FIELD_ID), (Long) map.get(CommonConstants.STREAM_ID), (Long) map.get(CommonConstants.FROM), (Long) map.get(CommonConstants.TO));
	}
	
	@Transactional
	public Object countByFieldIdAndStreamId(Map<String, Object> map) {
		val result = repository.countByFieldIdAndStreamId((Long) map.get(CommonConstants.FIELD_ID), (Long) map.get(CommonConstants.STREAM_ID));
		if (result != null && result.size() > 0)
			return result.get(0);
		return null;
	}
	
	@Transactional
	public void updateByQuestionId(String question, Long correctAnswer, Date updateDate, Long id) {
		repository.updateByQuestionId(question, correctAnswer, updateDate, id);
	}
	
	@Transactional
	public void updateById(String choice, Long id) {
		optionRepository.updateById(choice, id);
	}
	
	@Transactional
	public List<Object[]> selectRandomQuestion(Map<String, Object> map) {
		return repository.selectRandomQuestion(Long.parseLong(map.get(CommonConstants.FIELD_ID).toString()),
				Long.parseLong(map.get(CommonConstants.STREAM_ID).toString()),
				1, 
				Integer.parseInt(map.get(CommonConstants.BATCH_SIZE).toString()));
	}
	
	@Transactional
	public List<Object[]> selectRandomOption(Long qId) {
		return optionRepository.selectRandomOption(qId);
	}
	
	public int getBatchSize(Map<String, Object> map) {
		val fieldId = Long.parseLong(map.get(CommonConstants.FIELD_ID).toString());
		val streamId = Long.parseLong(map.get(CommonConstants.STREAM_ID).toString());

		if (fieldId == 1L) {
			val tech = techStreamRepo.findById(streamId);
			if (tech != null)
				return tech.getBatchSize();
		} else if (fieldId == 2L) {
			val nonTech = nonTechRepo.findById(streamId);
			if (nonTech != null)
				return nonTech.getBatchSize();
		}
		return 0;
	}
}
