package com.jayam.appservice.configuration.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.jayam.appservice.dto.QuestionDTO;

@Service
@CacheConfig(cacheNames={"questions","answers"})
public class AppCache {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(AppCache.class);

	private List<QuestionDTO> questionsList =new ArrayList<QuestionDTO>();
	private List<String> answerList =new ArrayList<String>();

	    @Caching(
	            put= { @CachePut(value= "questions", key= "#question.question") },
	            evict= { @CacheEvict(value= "questions", allEntries= true) }
	        )
	    public List<QuestionDTO> saveQuestion(QuestionDTO question) {
		   Optional<QuestionDTO> optional = questionsList.stream().filter(q->q.getQuestion().equalsIgnoreCase(question.getQuestion())).findAny();
 
		   if(!optional.isPresent()){
			   questionsList.add(question);
		   }
	        return questionsList;
	    }
	        
	
	    @Cacheable({"questions"})
	    public List<QuestionDTO> getAllQuestions() {
	        return questionsList;
	    }
	    
	    @Caching(
	            put= { @CachePut(value= "answers") },
	            evict= { @CacheEvict(value= "answers", allEntries= true) }
	        )
	    public List<String> saveAnswer(String answer) {
		   Optional<String> optional = answerList.stream().filter(q->q.equalsIgnoreCase(answer)).findAny();
 
		   if(optional.isPresent()){
			   LOGGER.info("Answer already exists in Cache");

		   }else{
			   answerList.add(answer);
		   }
	        return answerList;
	    }
	    
	    @Cacheable({"answers"})
	    public List<String> getAllAnswers() {
	        return answerList;
	    }

}
