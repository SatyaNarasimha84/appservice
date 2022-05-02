package com.jayam.appservice.web;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jayam.appservice.configuration.cache.AppCache;
import com.jayam.appservice.dto.QuestionDTO;
import com.jayam.appservice.service.SecurityQuestionService;
import com.jayam.appservice.utility.AppConstants;
import com.jayam.appservice.utility.AppServiceUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin
@RestController
@RequestMapping(AppConstants.PATH_QUESTION)
@Api(value = "Security Question Rest Controller	", protocols = "http")
public class SecurityQuestionController {
	public static final Logger LOGGER = LoggerFactory.getLogger(SecurityQuestionController.class);

	@Autowired
	SecurityQuestionService qaService;

	@Autowired
	AppCache appCache;

	@GetMapping(value=AppConstants.PATH_EMPTY,produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Request a security question ", response = String.class, 
	code = 200, notes = " Example : Hey Service, can you provide me a question with numbers to add?")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Response : Here You  Go solve the question: \"Please sum the numbers 5,11,13\""),
	        @ApiResponse(code = 400, message = "Not Valid Request"),
	        @ApiResponse(code = 401, message = "You are not authorized to view "),
	        @ApiResponse(code = 403, message = "Accessing the Question Controller, you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "Not found")
	})
	public ResponseEntity<String>   requestQuestion(@RequestParam(value = "text") String text) {
		if(!text.isEmpty()){
			String randomString = UUID.randomUUID().toString();
			String finalQuestion= qaService.getQuestionDetail(randomString,"add",2);
			return new  ResponseEntity<String>(finalQuestion,HttpStatus.OK);	
		}else{
			return new  ResponseEntity<String>("Not Valid Request",HttpStatus.BAD_REQUEST);
		}
			
	}

	@GetMapping(value=AppConstants.PATH_CLIENTRESPONSE,produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Send a response to security question ", response = String.class, 
	code = 200, notes = " Example : Great. The original question was \"Please sum the numbers 9,8\" and the answer is 17.")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "That’s great"),
	        @ApiResponse(code = 400, message = "Not Valid Request"),
	        @ApiResponse(code = 401, message = "You are not authorized to view "),
	        @ApiResponse(code = 403, message = "Accessing the Question Controller, you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "Not found")
	})
	public ResponseEntity<String>   submitAnswer(@RequestParam(value = "userInput") String userInput
			,HttpServletRequest request) {
	//	LOGGER.info("Client IP Address for validation  "+AppServiceUtility.getClientIp(request));
		if(!userInput.isEmpty()){
			String response = "";
			int beginIndex = userInput.indexOf("\"")+1;
			int lastIndex = userInput.lastIndexOf("\"");	
			String strSearch = userInput.substring(beginIndex, lastIndex);
			String strtAnswer = userInput.replaceAll(AppConstants.QUOTE, "");

			Integer totalSum =  AppServiceUtility.getNumberTotal(strSearch);
			Integer answer =    AppServiceUtility.getAnswer(strtAnswer);		
			
            /**
             * 1. Validating Question and Answer using Cache Mechanism
             * 2. Repeating
             */
			fetchCacheQuestionDetails(strSearch,answer);
			isAnswerExistsInCache(userInput); 
			
			if(!totalSum.equals(0) && !answer.equals(0) && totalSum.equals(answer)){
				response = "That’s great"; 
				return new  ResponseEntity<String>(response,HttpStatus.OK);	

			}
			response = "That’s wrong. Please try again"; 
			return new  ResponseEntity<String>(response,HttpStatus.BAD_REQUEST);
		}
		return new  ResponseEntity<String>("Not Valid  Request",HttpStatus.BAD_REQUEST);


	}

	private void fetchCacheQuestionDetails(String strSearch,Integer expectedValue) {
		List<QuestionDTO> questionsList =  appCache.getAllQuestions(); 
		LOGGER.info("Cache Question List :  "+questionsList);
		Optional<QuestionDTO> optional = questionsList.stream().filter(q->q.getQuestion().equalsIgnoreCase(strSearch)).findAny();
		if(optional.isPresent()){
			QuestionDTO data=optional.get();
			int grandTotal=data.getNumbers().stream().collect(Collectors.summingInt(Integer::intValue));
		  LOGGER.info("Filtered Question from Cache :  "+data);
		  LOGGER.info("Checking Client answer equals with data from cache : "+(grandTotal == expectedValue.intValue()));
		}
	}
	
	private void isAnswerExistsInCache(String answer) {
		List<String> list =  appCache.getAllAnswers(); 
		LOGGER.info("Cache Answer List :  "+list);
		if(list.contains(answer)){
			LOGGER.info("This same answer already submiited before...");
		}else{
			appCache.saveAnswer(answer);
		}

	}





}
