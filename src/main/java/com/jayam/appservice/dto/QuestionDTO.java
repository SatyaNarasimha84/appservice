package com.jayam.appservice.dto;

import java.util.List;

public class QuestionDTO {
	private String qid;
	private String question;
	private List<Integer> numbers;
	
	public QuestionDTO(){
		
	}

	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}

	public List<Integer> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numbers == null) ? 0 : numbers.hashCode());
		result = prime * result + ((qid == null) ? 0 : qid.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionDTO other = (QuestionDTO) obj;
		if (numbers == null) {
			if (other.numbers != null)
				return false;
		} else if (!numbers.equals(other.numbers))
			return false;
		if (qid == null) {
			if (other.qid != null)
				return false;
		} else if (!qid.equals(other.qid))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QuestionDTO [qid=" + qid + ", question=" + question + ", numbers=" + numbers + "]";
	}
	
	
	

}
