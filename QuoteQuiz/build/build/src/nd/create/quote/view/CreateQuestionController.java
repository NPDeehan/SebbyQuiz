package nd.create.quote.view;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import org.apache.commons.validator.routines.UrlValidator;
import org.controlsfx.dialog.Dialogs;

import nd.create.quote.MainApp;
import nd.create.quote.model.QuizAns;
import nd.create.quote.model.QuizQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;



public class CreateQuestionController {
	
	private Stage dialogStage;
	
	private MainApp mainApp;
	
	private boolean editMode;;
	
	@FXML
	private TextField questionText;
	@FXML
	private TextField answerAText;
	@FXML
	private TextField answerBText;
	@FXML
	private TextField answerCText;
	@FXML
	private TextField answerDText;
	@FXML
	private TextField questionNameText;

	
	@FXML
	private Label questionNameLable;
	@FXML
	private Label questionSourceLable;
	
	@FXML
	private Button advancedOptions;
	
	@FXML
	private ToggleGroup radioButtonGroup = new ToggleGroup();
	
	@FXML
	private RadioButton radioButtonAnsA;
	@FXML
	private RadioButton radioButtonAnsB;
	@FXML
	private RadioButton radioButtonAnsC;
	@FXML
	private RadioButton radioButtonAnsD;
	
	private boolean submitClicked = false;

	private QuizQuestion quizQuestion;
	private QuizAns quizAnsA = new QuizAns();
	private QuizAns quizAnsB = new QuizAns();
	private QuizAns quizAnsC = new QuizAns();
	private QuizAns quizAnsD = new QuizAns();

	public CreateQuestionController() 
	{
	
	}
	
	public void initialiseForEdit(QuizQuestion question)
	{
		this.quizQuestion = question;
		this.quizAnsA = quizQuestion.getAnswer(1);
		this.quizAnsB = quizQuestion.getAnswer(2);
		this.quizAnsC = quizQuestion.getAnswer(3);
		this.quizAnsD = quizQuestion.getAnswer(4);
		
		questionNameText.setText(question.getShortName());
		questionText.setText(question.getQuestion());
		answerAText.setText(question.getAnswer(1).getAnswer());
		answerBText.setText(question.getAnswer(2).getAnswer());
		answerCText.setText(question.getAnswer(3).getAnswer());
		answerDText.setText(question.getAnswer(4).getAnswer());
		
		radioButtonAnsA.setSelected(question.getAnswer(1).isCorrect());
		radioButtonAnsB.setSelected(question.getAnswer(2).isCorrect());
		radioButtonAnsC.setSelected(question.getAnswer(3).isCorrect());
		radioButtonAnsD.setSelected(question.getAnswer(4).isCorrect());
		  
		editMode = true;
	}
	
	@FXML
    private void initialize() {
		radioButtonAnsA.setToggleGroup(radioButtonGroup);
		radioButtonAnsB.setToggleGroup(radioButtonGroup);
		radioButtonAnsC.setToggleGroup(radioButtonGroup);
		radioButtonAnsD.setToggleGroup(radioButtonGroup);
		
		editMode = false;
    }
	
	 public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	   }
	 
	 /**
	     * Is called by the main application to give a reference back to itself.
	     * 
	     * @param mainApp
	     */
	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;
	        
	    }
	 
	  @FXML
	  public void handleClose()
	  {
		  dialogStage.close();
	  }
	  
	  
	  @FXML
	  public void handleSubmit()
	  {
		  
		  boolean validInput = isInputQuestionValid();
		  if (!validInput){return;}
		  
		  quizAnsA.setAnswer(answerAText.getText());
		  quizAnsA.setCorrect(radioButtonAnsA.isSelected());
		  
		  quizAnsB.setAnswer(answerBText.getText());
		  quizAnsB.setCorrect(radioButtonAnsB.isSelected());
		  
		  quizAnsC.setAnswer(answerCText.getText());
		  quizAnsC.setCorrect(radioButtonAnsC.isSelected());
		  
		  quizAnsD.setAnswer(answerDText.getText());
		  quizAnsD.setCorrect(radioButtonAnsD.isSelected());
	  
		  Vector<QuizAns> answers = new Vector<QuizAns>(4);
		  answers.add(quizAnsA);
		  answers.add(quizAnsB);
		  answers.add(quizAnsC);
		  answers.add(quizAnsD);
		  
		  if(!editMode){
			  quizQuestion = new QuizQuestion(questionText.getText(), answers);
			  quizQuestion.setShortName(questionNameText.getText());
			  mainApp.addQuestionToList(quizQuestion);
		  }else{
			  // if we're editing we want to use the same object
			  quizQuestion.setQuestion(questionText.getText());
			  quizQuestion.setAnswers(answers);
			  quizQuestion.setShortName(questionNameText.getText());
		  }
		  		  
		  
		  submitClicked = true;
		  dialogStage.close();		  
	  }
	  
	  @FXML
	  public void handleSourceAddedAnsA()
	  {
		  mainApp.showSourceDialog(quizAnsA);
	  }
	  
	  @FXML
	  public void handleSourceAddedAnsB()
	  {
		  mainApp.showSourceDialog(quizAnsB);
	  }
	  @FXML
	  public void handleSourceAddedAnsC()
	  {
		  mainApp.showSourceDialog(quizAnsC);
	  }
	  @FXML
	  public void handleSourceAddedAnsD()
	  {
		  mainApp.showSourceDialog(quizAnsD);
	  }
	  
	  
	  private void addASourceToAnAnswer(QuizAns quizAns)
	  {
		  String sourceTextInput = "Add a URL for reference here";
		  
		  if(quizAns.getSourceString() != null)
			  sourceTextInput = quizAns.getSourceString();
		  
		  Optional<String> response = Dialogs.create()
			        .title("Text Input Dialog")
			        .masthead("Look, a Text Input Dialog")
			        .message("Enter your source URL here")
			        .showTextInput(sourceTextInput);
		  
		  if (response.isPresent()) {
			    String url = response.get();
			    if(!url.startsWith("http://"))
			    	url = "http://"+url;
			    UrlValidator urlValidator = new UrlValidator();
			    boolean isValidURL = urlValidator.isValid(url);
			    //boolean isValidURL = true;
			    if(!isValidURL)
			    {
					Dialogs.create()
					.title("Nope")
					.masthead(null)
					.message("Sorry, That was not a valid URL")
					.showError();
					return;
			    }else{
			    	try {
						quizAns.setSourceURL(new URL(url));
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			}
	  }
	  
	  
	  private boolean isInputQuestionValid()
	  {
		  if(questionText.getText().equals(""))
		  {
				Dialogs.create()
				.title("No Question Entered")
				.masthead("Please Enter a Question")
				.message("Seriously though, you just tried to submit a blank question. Perhaps thats a symptom of a greater issue. ")
				.showError();
				
				return false;
		  }
		  if(answerAText.getText().equals("") || answerBText.getText().equals("") || answerCText.getText().equals("") || answerDText.getText().equals(""))
		  {
			  Dialogs.create()
				.title("Answers are missing")
				.masthead("Please add 4 Possible Answers")
				.message("Otherwise it would be too easy, don't assume people are that stupid")
				.showError();
				
				return false;
		  }
		  if(questionNameText.getText().equals(""))
		  {
			  Dialogs.create()
				.title("Missing Title")
				.masthead("Give your question a name")
				.message("Like you would a child or an adorable goat.")
				.showError();
				
				return false;
		  }
		  
		  return true;
	  }
	  
	  public boolean isSubmitClicked()
	  {
		  return submitClicked;
	  }
	  


}
