package nd.create.quote.model;

import java.awt.List;
import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * This is an object that hold a question along with a vector of answers.
 * 
 * @author Seb
 *
 */


public class QuizQuestion {
	
	private String shortName = "";
	
	private String question;
	
	private boolean questionAnswered = false;
	
	private Vector<QuizAns> answers = new Vector<QuizAns>(4);

	public QuizQuestion() {
		
	}
	

	public QuizQuestion(String quizQuestion, Vector<QuizAns> quizAnswers) 
	{
		//need to make sure that there are no more than 4 answers at some point
		
		
		answers = quizAnswers;
		question = quizQuestion;
		shortName = "Question Bob";
		
	}

	public String getShortName() {
		return shortName;
	}
	
	public StringProperty getShortNameProperty(){
		StringProperty thisString = new SimpleStringProperty(shortName);
		
		return thisString;
	}


	public void setShortName(String shortName) {
		this.shortName = shortName;
	}


	public String getQuestion() {
		return question;
	}


	public void setQuestion(String question) {
		this.question = question;
	}


	public Vector<QuizAns> getAnswers() {
		return answers;
	}


	public void setAnswers(Vector<QuizAns> answers) {
		this.answers = answers;
	}
	
	public QuizAns getAnswer(int number)
	{
		number--;
		
		QuizAns questionToReturn = answers.get(number);
		if(questionToReturn == null)
		{
			System.out.println("No Question with that number found");
			return null;
		}
		
		return questionToReturn;
	}


	public boolean isQuestionAnswered() {
		return questionAnswered;
	}


	public void setQuestionAnswered(boolean questionAnswered) {
		this.questionAnswered = questionAnswered;
	}


}
