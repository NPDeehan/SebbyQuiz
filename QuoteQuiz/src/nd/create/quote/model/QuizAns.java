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
 * This object is an answer to a quiz question, it contains the text of the answer, a boolean descibling weather it is corret 
 * or not and also an optional response for when the user guesses it. 
 * @author Seb
 *
 */

public class QuizAns {
	
	private String answer;

	private boolean correct;
	
	private String response;
	
	private String sourceString;
	
	private URL sourceURL;
	
	private boolean answerChosen = false;

	public QuizAns(String quizAnswer, boolean correctAns) {
		
		answer = quizAnswer;
		correct = correctAns;
		response = "";
		sourceString = "";
		sourceURL = null;

			
			
	}
	
	public QuizAns(String quizAnswer, boolean correctAns, String guessResponse) {
		answer = quizAnswer;
		correct = correctAns;
		response = guessResponse;
		sourceString = "";
		sourceURL = null;
	}
	
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
