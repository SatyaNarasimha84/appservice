package com.jayam.appservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SecurityQuestionServiceTest {
	    @Mock
	    private SecurityQuestionService securityQuestionService ;
	  
  
	  
	    @Test
	    void getQuestionDetail() {
		   String randomString = "Hey Service, can you provide me a question with numbers to add?";
	    	String mockResponse ="Here You  Go solve the question: \"Please sum the numbers 5,11,13\"";

	        when(securityQuestionService.getQuestionDetail(randomString,"add",2)).thenReturn("Test");
	        assertEquals(securityQuestionService.getQuestionDetail(randomString,"add",2),"Test");
	    }

}
