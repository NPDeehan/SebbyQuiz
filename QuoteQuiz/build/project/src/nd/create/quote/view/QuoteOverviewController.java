package nd.create.quote.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.controlsfx.dialog.Dialogs.CommandLink;
import org.controlsfx.control.action.Action;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nd.create.quote.MainApp;
import nd.create.quote.model.QuizAns;
import nd.create.quote.model.QuizQuestion;
import nd.create.quote.utils.URLHelper;

public class QuoteOverviewController {
	
	private MainApp mainApp;
	
	private HashMap<Button, QuizAns> buttonsAndAnswers = new HashMap<Button, QuizAns>();
	
    @FXML
    private TableView<QuizQuestion> questionsTable;
    @FXML
    private TableColumn<QuizQuestion, String> questionNameColumn;
	
	@FXML
	private Button answerAButton;
	@FXML
	private Button answerBButton;
	@FXML
	private Button answerCButton;
	@FXML
	private Button answerDButton;
	
	@FXML
	private Label questionLabel;
	
	@FXML
	private Button editQuestionButton;
	@FXML
	private Button deleteQuestionButton;
	@FXML
	private Button tryAgainButton;
	
	@FXML
	private ImageView weirdImage;
	
	private QuizQuestion currentQuizQuestion;
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        setAdminMode(mainApp.isAdminMode());
        questionsTable.setItems(mainApp.getQuestionData());
        
    }


	public QuoteOverviewController() {
		// TODO Auto-generated constructor stub
	}
	
	@FXML
    private void initialize() {

		questionNameColumn.setCellValueFactory(
	                cellData -> cellData.getValue().getShortNameProperty());
		
		questionsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setQuestion(newValue));
		questionsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setQuestion(newValue));
		
		
		deactivateAnsButtons(true);
		setDefualtButtonText();

    }
	/**
	 * This Method should clear all local variables in preperation for a new question.
	 */
	private void resetQuestion()
	{
		// set all the buttons back to disabled
		deactivateAnsButtons(true);
		setDefualtButtonText();
	      
	      // clear the buttons and answers hashmap
		buttonsAndAnswers = new HashMap<Button, QuizAns>();
	      
	      //reset the question lable
		questionLabel.setText("");
		
		//reset the current question
		currentQuizQuestion = null;
		
	}
	
	private void setDefualtButtonText()
	{
	      answerAButton.setText("A");
	      answerAButton.setGraphic(null);
	      
	      answerBButton.setText("B");
	      answerBButton.setGraphic(null);
	      
	      answerCButton.setText("C");
	      answerCButton.setGraphic(null);
	      
	      answerDButton.setText("D");
	      answerDButton.setGraphic(null);
	}
	
	private void deactivateAnsButtons(boolean active)
	{
	      answerAButton.setDisable(active);
	      answerBButton.setDisable(active);
	      answerCButton.setDisable(active);
	      answerDButton.setDisable(active);
	}
	
	
	@FXML
	private void handleQuizTime()
	{
		resetQuestion();
		
		Dialogs.create()
		.title("It Starts!")
		.masthead("Prepare Yourself")
		.message("Quiz time approches")
		.showInformation();
		
		//setDefaultQuestion();
		
		//deactivateAnsButtons(true);
	}
	
	@FXML
	private void handleTryAgain()
	{
		resetQuestion();
		mainApp.resetChoicesQuestionsAndAnswers();
		if(!mainApp.isAdminMode())
			tryAgainButton.setVisible(false);
	}



	
	public void setQuestion(QuizQuestion quizQuestion)
	{
		resetQuestion();
		
		if(quizQuestion != null)
		{

				questionLabel.setText(quizQuestion.getQuestion());
				
				answerAButton.setText(quizQuestion.getAnswer(1).getAnswer());
				buttonsAndAnswers.put(answerAButton, quizQuestion.getAnswer(1));
				
				answerBButton.setText(quizQuestion.getAnswer(2).getAnswer());
				buttonsAndAnswers.put(answerBButton, quizQuestion.getAnswer(2));
				
				answerCButton.setText(quizQuestion.getAnswer(3).getAnswer());
				buttonsAndAnswers.put(answerCButton, quizQuestion.getAnswer(3));
				
				answerDButton.setText(quizQuestion.getAnswer(4).getAnswer());
				buttonsAndAnswers.put(answerDButton, quizQuestion.getAnswer(4));
				
				currentQuizQuestion = quizQuestion;
				
				//if the question has already been answered then we just want to display what the user ended up going for.
				if(quizQuestion.isQuestionAnswered() && !mainApp.isAdminMode())
				{
					deactivateAnsButtons(true);
					if(quizQuestion.getAnswer(1).isAnswerChosen())
					{
						changeButtonsAfterAns(answerAButton, quizQuestion.getAnswer(1).isCorrect());
					}
					if(quizQuestion.getAnswer(2).isAnswerChosen())
					{
						changeButtonsAfterAns(answerBButton, quizQuestion.getAnswer(2).isCorrect());
					}
					if(quizQuestion.getAnswer(3).isAnswerChosen())
					{
						changeButtonsAfterAns(answerCButton, quizQuestion.getAnswer(3).isCorrect());
					}
					if(quizQuestion.getAnswer(4).isAnswerChosen())
					{
						changeButtonsAfterAns(answerDButton, quizQuestion.getAnswer(4).isCorrect());
					}
				}else{
					deactivateAnsButtons(false);
				}
		} else{
			resetQuestion();
		}
		
	}
	
	@FXML
	public void handelGuessA()
	{
		
		QuizAns quizAns = buttonsAndAnswers.get(answerAButton);
		changeButtonsAfterGuess(answerAButton, checkGuess(quizAns));
		mainApp.loadNextQuestion();

	}
	@FXML
	public void handelGuessB()
	{

		QuizAns quizAns = buttonsAndAnswers.get(answerBButton);
		changeButtonsAfterGuess(answerBButton, checkGuess(quizAns));
		mainApp.loadNextQuestion();
	}
	@FXML
	public void handelGuessC()
	{

		QuizAns quizAns = buttonsAndAnswers.get(answerCButton);
		changeButtonsAfterGuess(answerCButton, checkGuess(quizAns));
		mainApp.loadNextQuestion();
	}
	@FXML
	public void handelGuessD()
	{

		QuizAns quizAns = buttonsAndAnswers.get(answerDButton);	
		changeButtonsAfterGuess(answerDButton, checkGuess(quizAns));
		mainApp.loadNextQuestion();
		
	}
	/**
	 * This method will set the correct/incorrect icon on the clicked button
	 * as well as disabling other buttons - unless admin mode is active
	 * 
	 * @param button
	 * @param rightAns
	 */
	private void changeButtonsAfterAns(Button button, boolean rightAns)
	{
		if (rightAns) {
			button.setGraphic(new ImageView(mainApp.getCorrectImage()));
		} else {
			
			button.setGraphic(new ImageView(mainApp.getIncorrectImage()));
		}
		
	}
	
	private void changeButtonsAfterGuess(Button button, boolean rightAns)
	{
		if (rightAns) {
			button.setGraphic(new ImageView(mainApp.getCorrectImage()));
		} else {
			
			button.setGraphic(new ImageView(mainApp.getIncorrectImage()));
		}
		
		if(!mainApp.isAdminMode())
		{
			questionAnswered();
			if(mainApp.areAllQustionsAnswered())
			{
				tryAgainButton.setVisible(true);
				mainApp.showChartOfVictory();
			}
		}
	}

	private void questionAnswered() {
		
		deactivateAnsButtons(true);
		currentQuizQuestion.setQuestionAnswered(true);
	}


	private boolean checkGuess(QuizAns quizAns)
	{
		
		if(quizAns.isCorrect()){
			correctAns(quizAns);
			quizAns.setAnswerChosen(true);
			return true;
			
		}else{
			wrongAns(quizAns);
			quizAns.setAnswerChosen(true);
			return false;
		}

	}
	
	private void wrongAns(QuizAns quizAns)
	{
		URL questionSource = quizAns.getSourceURL();
		List<CommandLink> links = new ArrayList<>();
		if(questionSource == null || questionSource.equals(null)){

			links.add(new CommandLink("Bad Luck", quizAns.getResponse()));
		}else{
			links.add(new CommandLink("Click Here to find out why you're wrong", quizAns.getResponse()));
		}
		

		 Action response = Dialogs.create()
		        .title("Guess Dialog")
		        .masthead("Sorry, you guessed incorrectly")
		        .message("WRONG!")
		        .showCommandLinks(links.get(0), links);

		System.out.println(response);
		
		if(response != Dialog.Actions.CANCEL)
		{
			URLHelper urlHelper = new URLHelper();
			if(quizAns.getSourceURL() != null)
				urlHelper.openWebpage(quizAns.getSourceURL());
			
		}
	}
	
	private void correctAns(QuizAns quizAns)
	{
		URL questionSource = quizAns.getSourceURL();
		List<CommandLink> links = new ArrayList<>();
		if(questionSource == null || questionSource.equals(null)){

			links.add(new CommandLink("Yay!", quizAns.getResponse()));
		}else{
			links.add(new CommandLink("Click Here to find out more", quizAns.getResponse()));
		}
		

		 Action response = Dialogs.create()
		        .title("Guess Dialog")
		        .masthead("You've got the right answer")
		        .message("CORRECT!")
		        .showCommandLinks(links.get(0), links);

		System.out.println(response);
		
		if(response != Dialog.Actions.CANCEL)
		{
			URLHelper urlHelper = new URLHelper();
			if(quizAns.getSourceURL() != null)
				urlHelper.openWebpage(quizAns.getSourceURL());
			
		}

	}
	
	@FXML
	private void handelEdit()
	{
		QuizQuestion qest = questionsTable.getSelectionModel().getSelectedItem();

		if (qest != null) {
            boolean okClicked = mainApp.editQuestionLaunch(qest);
            if (okClicked) {
                resetQuestion();
            }

        } else {
            // Nothing selected.
            Dialogs.create()
                .title("No Selection")
                .masthead("No Person Selected")
                .message("Please select a person in the table.")
                .showWarning();
        }
	}
	
	@FXML
    private void handleDeleteQuestion() {
        int selectedIndex = questionsTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex>=0){
        	questionsTable.getItems().remove(selectedIndex);
        }else {
        	Dialogs.create()
        		.title("No Selection")
        		.masthead("No Question Selected")
        		.message("Please select a question in the table.")
        		.showWarning();
        }
    
    }
	
	public boolean setAdminMode(boolean adminMode)
	{
		resetQuestion();
		editQuestionButton.setVisible(adminMode);
		deleteQuestionButton.setVisible(adminMode);
		tryAgainButton.setVisible(adminMode);
		
		return true;
	}



}
