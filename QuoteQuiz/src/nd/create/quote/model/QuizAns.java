package nd.create.quote.model;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;






import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;

import nd.create.quote.utils.URLAdapter;



/**
 * This object is an answer to a quiz question, it contains the text of the answer, 
 * a boolean descibling weather it is correct 
 * or not and also an optional response for when the user guesses it. 
 * There is also a boolean that holds if this answer has been selected by the user.
 * 
 * @author Seb
 *
 */

public class QuizAns {
	//This is the text of the answer
	private String answer;
	//This defines if the answer is correct or not
	private boolean correct;
	//This is optional text that will appear if a user guesses this answer
	private String response;
	//This is an option Sting value of a URL that will take a user to the source of the answer
	private String sourceString;
	//This is a URL object that will take a user to the source of the answer
	private URL sourceURL;
	//If a user has chosen this answer it will be set to true.
	private boolean answerChosen = false;

	/**
	 * This is a constructor that takes a quiz answer string and if it is correct
	 * @param quizAnswer
	 * @param correctAns
	 */
	public QuizAns(String quizAnswer, boolean correctAns) {
		
		answer = quizAnswer;
		correct = correctAns;
		response = "";
		sourceString = "";
		sourceURL = null;

	}
	/**
	 * This is a constructor that takes a quiz answer string, if it is correct and a response string
	 * @param quizAnswer
	 * @param correctAns
	 * @param guessResponse
	 */
	public QuizAns(String quizAnswer, boolean correctAns, String guessResponse) {
		answer = quizAnswer;
		correct = correctAns;
		response = guessResponse;
		sourceString = "";
		sourceURL = null;
	}
	/**
	 * default constructor
	 */
	public QuizAns()
	{
		answer = "";
		response = "";
		sourceString = "";
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public boolean isCorrect() {
		return correct;
	}
	
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	
	public String getResponse() {
		return response;
	}
	
	public void setResponse(String response) {
		this.response = response;
	}

	public URL getSourceURL() {
		return sourceURL;
	}
	
	public String getSourceString(){
		return sourceString;
	}

	public void setSourceURL(URL newSourceURL){
		
		sourceURL = newSourceURL;
		try {
			URI uriString = sourceURL.toURI();
			sourceString = uriString.toString();
			System.out.println(sourceString);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sourceString = newSourceURL.toString();
	}
	
	public boolean setSource(String source) {
		
		try {
			sourceString = source;
			sourceURL = new URL(source);
		} catch (MalformedURLException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	@XmlJavaTypeAdapter(URLAdapter.class)
	public URL getSource() {
	    return sourceURL;
	}

	public boolean isAnswerChosen() {
		return answerChosen;
	}

	public void setAnswerChosen(boolean answerChosen) {
		this.answerChosen = answerChosen;
	}
	
}
