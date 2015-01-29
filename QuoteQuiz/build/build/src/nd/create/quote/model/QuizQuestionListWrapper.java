package nd.create.quote.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;




@XmlRootElement(name = "quizQuestions")
public class QuizQuestionListWrapper {
	
	private List<QuizQuestion> quizQuestions;
	
    @XmlElement(name = "quizQuestion")
    public List<QuizQuestion> getQuizQuestions() {
        return quizQuestions;
    }
    
    public void setQuizQuestions(List<QuizQuestion> questions)
    {
    	quizQuestions = questions;
    }

	public QuizQuestionListWrapper() {
		// TODO Auto-generated constructor stub
	}

}
