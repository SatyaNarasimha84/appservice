package com.jayam.appservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jayam.appservice.configuration.cache.AppCache;
import com.jayam.appservice.dto.QuestionDTO;
import com.jayam.appservice.utility.AppConstants;
import com.jayam.appservice.utility.AppServiceUtility;

@Service
public class SecurityQuestionServiceImpl implements SecurityQuestionService {
	
	@Autowired
	AppCache appCache;
	
	public String getQuestionDetail(String randomString , String questionType, int numericValues ) {
		//Question Type to extend for other operations
		List<Integer> numbers;
		numericValues =("add".equalsIgnoreCase(questionType))
				? AppServiceUtility.getRandomNumberInRange(2,3): 2;
		numbers = AppServiceUtility.generateNumbersList(numericValues); 
		String strNumbers = numbers.stream().map(Object::toString).collect(Collectors.joining(","));
		StringBuffer sb = new StringBuffer();
		String strQuestion= sb.append(AppConstants.QUOTE).append(AppConstants.QUESTION_ADD).append(strNumbers).append(AppConstants.QUOTE).toString();
	
		QuestionDTO question=new QuestionDTO();
		question.setQid(randomString);
		question.setQuestion(AppConstants.QUESTION_ADD+strNumbers);
		question.setNumbers(numbers);
		appCache.saveQuestion(question);
		
		return (AppConstants.GENERIC_STRING +  strQuestion);
	}

}
