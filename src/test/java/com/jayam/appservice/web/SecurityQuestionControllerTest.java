package com.jayam.appservice.web;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import com.jayam.appservice.service.SecurityQuestionService;
 
@ExtendWith(MockitoExtension.class)
public class SecurityQuestionControllerTest 
{
    @InjectMocks
    SecurityQuestionController securityQuestionController;
     
    @Mock
    SecurityQuestionService securityQuestionService;
     
    @Test
    public void testRequestQuestion() 
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        when(securityQuestionService.getQuestionDetail(any(String.class),any(String.class),any(Integer.class))).thenReturn("Test");
    	String queryParam = "Hey Service, can you provide me a question with numbers to add?";

        ResponseEntity<String> responseEntity = securityQuestionController.requestQuestion(queryParam);
         
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);    }
     
 }
