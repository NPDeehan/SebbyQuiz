package nd.create.quote;

import java.io.File;
import java.io.IOException;





import java.util.List;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.controlsfx.dialog.Dialogs;

import nd.create.quote.model.QuizAns;
import nd.create.quote.model.QuizQuestion;
import nd.create.quote.model.QuizQuestionListWrapper;
import nd.create.quote.view.AboutDialogController;
import nd.create.quote.view.ChartOfVictoryDialogController;
import nd.create.quote.view.CreateQuestionController;
import nd.create.quote.view.QuoteOverviewController;
import nd.create.quote.view.RootLayoutController;
import nd.create.quote.view.SourceDialogController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {
	
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private boolean adminMode = false;
    
    private QuoteOverviewController quoteOverviewController;
    private RootLayoutController rootLayoutController;
    
    private ObservableList<QuizQuestion> quizQuestions = FXCollections.observableArrayList();
    
	public MainApp() {
			
//		QuizQuestion testQuestionOne = setUpDefaultQuestionOne();
//		QuizQuestion testQuestionTwo = setUpDefaultQuestionTwo();
//		quizQuestions.add(testQuestionOne);
//		quizQuestions.add(testQuestionTwo);
	}
	
	/**
	 * This will add a question to the global list.
	 * @param question
	 */
	public void addQuestionToList(QuizQuestion question)
	{
		quizQuestions.add(question);
	}
	
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    /*
     * Launches the application
     */
    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SebQuoteQuiz");
        this.primaryStage.getIcons().add(new Image("file:resources/images/fallout.png"));

        initRootLayout();

        showQuoteOverview();
		
	}
	
    /**
     * Returns the data as an observable list of Questions. 
     * @return
     */
    public ObservableList<QuizQuestion> getQuestionData() {
        return quizQuestions;
    }
    
	/**
	 * This can be used for debugging and testing, It's currently not being used
	 * @return
	 */
	private QuizQuestion setUpDefaultQuestionOne()
	{

		Vector<QuizAns> answers = new Vector<QuizAns>(4);
		
		QuizAns answerA = new QuizAns("42", true);
		answerA.setSource("https://www.google.de/?gfe_rd=cr&ei=PwSrVKD-EaeG8Qf704HwCA&gws_rd=ssl#q=what+is+the+meaning+of+life+the+universe+and+everything");
		answers.add(answerA);
		
		QuizAns answerB = new QuizAns("Fallout 4", false);
		answers.add(answerB);
		
		QuizAns answerC = new QuizAns("Really Nice Beer", false);

		answers.add(answerC);
		
		QuizAns answerD = new QuizAns("All of the above", false);
		answers.add(answerD);
		
		QuizQuestion defaultQuestoin = new QuizQuestion("What is the answer to life the universe and everything?", answers);
		defaultQuestoin.setShortName("Life etc.");
		
		
		return defaultQuestoin;
	}
	
	/**
	 * This can be used for debugging and testing, It's currently not being used
	 * @return
	 */
	private QuizQuestion setUpDefaultQuestionTwo()
	{

		Vector<QuizAns> answers = new Vector<QuizAns>(4);
		
		QuizAns answerA = new QuizAns("His sidekick is a wise-craking giraff", true);
		answerA.setSource("https://soundcloud.com/village-sound/the-flop-house-rocket-crocodile-auto-tune");
		answerA.setResponse("Correct - Rocket Crocodile is an under-construction screenplay that you'd really love, i promise");
		answers.add(answerA);
		
		QuizAns answerB = new QuizAns("He kills a guy with a submarine sandwich", false);
		answers.add(answerB);
		
		QuizAns answerC = new QuizAns("It get 5 out of 5 amazings", false);

		answers.add(answerC);
		
		QuizAns answerD = new QuizAns("He becomes a rockstar in the past", false);
		answers.add(answerD);
		
		QuizQuestion defaultQuestoin = new QuizQuestion("Which of these is not a fact about Rocket Crockodile and the world of Tomorrow", answers);
		defaultQuestoin.setShortName("Rocket Crock");
		return defaultQuestoin;

	}
	
	/**
	 * This will show the Quote overview panel that displays the question. 
	 */
	 private void showQuoteOverview() {
	    	try {
	            // Load question overview.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("view/QuoteOverview.fxml"));
	            AnchorPane personOverview = (AnchorPane) loader.load();

	            // Set question overview into the center of root layout.
	            rootLayout.setCenter(personOverview);

	            // Give the controller access to the main app.
	            QuoteOverviewController controller = loader.getController();
	            controller.setMainApp(this);
	            quoteOverviewController = controller;

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}
	 
	  /**
	     * Opens a dialog to show ABOUT
	     * The about dialog also controlls if Admin Mode is active or not.
	     */
	    public void showAbout() {
	        try {
	            // Load the fxml file and create a new stage for the popup.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("view/AboutDialog.fxml"));
	            AnchorPane page = (AnchorPane) loader.load();
	            Stage dialogStage = new Stage();
	            dialogStage.setTitle("About");
	            dialogStage.initModality(Modality.WINDOW_MODAL);
	            dialogStage.initOwner(primaryStage);
	            Scene scene = new Scene(page);
	            dialogStage.setScene(scene);

	            
	            AboutDialogController controller = loader.getController();
	            controller.setDialogStage(dialogStage);
	            // Get the weird image and add it to the dialog
	            Image oddImage = new Image("file:resources/images/J8oocez.gif");
	            
	            controller.setupImage(oddImage);
	            controller.setMainApp(this);
	            dialogStage.show();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    /**
	     * Opens a dialog in which an admin user will be able to create a question for a quiz 
	     * being made.
	     */
	    public void showCreateQuestion() {
	        try {
	            // Load the fxml file and create a new stage for the popup.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("view/CreateQuestionDialog.fxml"));
	            AnchorPane page = (AnchorPane) loader.load();
	            Stage dialogStage = new Stage();
	            dialogStage.setTitle("Create A Question");
	            dialogStage.initModality(Modality.WINDOW_MODAL);
	            dialogStage.initOwner(primaryStage);
	            Scene scene = new Scene(page);
	            dialogStage.setScene(scene);

	            CreateQuestionController controller = loader.getController();
	            controller.setDialogStage(dialogStage);
	            controller.setMainApp(this);
	            
	            dialogStage.show();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    /**
	     * Little chat that shows up at the end of the quiz so that the user can see how well they did
	     */
	    public void showChartOfVictory()
	    {
	    	try{
		    	 // Load the fxml file and create a new stage for the popup.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("view/ChartOfVictoryDialog.fxml"));
	            AnchorPane page = (AnchorPane) loader.load();
	            Stage dialogStage = new Stage();
	            dialogStage.setTitle("Pie of Victory");
	            dialogStage.initModality(Modality.WINDOW_MODAL);
	            dialogStage.initOwner(primaryStage);
	            Scene scene = new Scene(page);
	            dialogStage.setScene(scene);
	

	            ChartOfVictoryDialogController controller = loader.getController();
	            controller.setDialogStage(dialogStage);
	            controller.initialize(quizQuestions);

	            
	            dialogStage.show();
	    	  } catch (IOException e) {
		            e.printStackTrace();
		      }
	    }
	    
	    /**
	     * A quiz answer can contain a Source for the answer given, it's optional but if a user
	     * chooses to do it, this is the dialog that will help them do that.
	     * @param quizAns
	     * @return
	     */
	    public boolean showSourceDialog(QuizAns quizAns){
	    	try{
	    		
		    	FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("view/SourceDialog.fxml"));
	            AnchorPane page = (AnchorPane) loader.load();
	            Stage dialogStage = new Stage();
	            dialogStage.setTitle("Creat a Source");
	            dialogStage.initModality(Modality.WINDOW_MODAL);
	            dialogStage.initOwner(primaryStage);
	            Scene scene = new Scene(page);
	            dialogStage.setScene(scene);
	            
	            SourceDialogController controller = loader.getController();
	            controller.setDialogStage(dialogStage);
	            controller.initializeData(quizAns);
	            dialogStage.show();
	            
	            return true;
	    	}
            catch(Exception e){
            	e.printStackTrace();
            	return false;
            }
            
	    }
	    
	    /**
	     * This allows an Admin user to edit a question.
	     * @param question
	     * @return
	     */
	    public boolean editQuestionLaunch(QuizQuestion question) {
	    	 try {
		            // Load the fxml file and create a new stage for the popup.
		            FXMLLoader loader = new FXMLLoader();
		            loader.setLocation(MainApp.class.getResource("view/CreateQuestionDialog.fxml"));
		            AnchorPane page = (AnchorPane) loader.load();
		            Stage dialogStage = new Stage();
		            dialogStage.setTitle("Edit A Question");
		            dialogStage.initModality(Modality.WINDOW_MODAL);
		            dialogStage.initOwner(primaryStage);
		            Scene scene = new Scene(page);
		            dialogStage.setScene(scene);

		            // Set the persons into the controller.
		            CreateQuestionController controller = loader.getController();
		            controller.setDialogStage(dialogStage);
		            controller.setMainApp(this);
		            controller.initialiseForEdit(question);
		            
		            dialogStage.show();
		            return true;

		        } catch (IOException e) {
		            e.printStackTrace();
		            return false;
		        }
	    	 
		    
	    }

	/**
     * Initializes the root layout and tries to load the last opened
     * question file.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            rootLayoutController = controller;

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = getQuestionFilePath();
        if (file != null) {
            loadQuestionDataFromFile(file);
        }

    }
    

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     * 
     * @param file the file or null to remove the path
     */
    public void setQuestionFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("SebQuoteQuiz - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("SebQuoteQuiz");
        }
    }
    
    /**
     * This returns the image for an incorrect answer - it is attached to the selected button
     * @return
     */
    public Image getIncorrectImage()
    {
    	Image IncorrectImage = new Image("file:resources/images/Wrong2.png");
    	
    	return IncorrectImage;
    }
    /**
     * This returns the image for an correct answer - it is attached to the selected button
     * @return
     */
    public Image getCorrectImage()
    {
    	return new Image("file:resources/images/Correct3.png");
    }
    /**
     * This get the questions from an XML file and then adds them to the global variable that stores
     * the questions.
     * @param file
     */
    public void loadQuestionDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(QuizQuestionListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            QuizQuestionListWrapper wrapper = (QuizQuestionListWrapper) um.unmarshal(file);

            quizQuestions.clear();
            quizQuestions.addAll(wrapper.getQuizQuestions());

            // Save the file path to the registry.
            setQuestionFilePath(file);

        } catch (Exception e) { 
            Dialogs.create()
                    .title("Error")
                    .masthead("Could not load data from file:\n" + file.getPath())
                    .showException(e);
        }
    }
    
    /**
     * Returns the question file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     * 
     * @return
     */
    public File getQuestionFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
        	System.out.println("Returing new File..");
            return new File(filePath);
        } else {
        	System.out.println("Returing nothing.. :( ");
            return null;
        }
    }
    
    /**
     * Saves the current question data to the specified file.
     * 
     * @param file
     */
    public void saveQuestionDataToFile(File file) {
        try {
        	System.out.println("Going to save data to file.");
        	
            JAXBContext context = JAXBContext
                    .newInstance(QuizQuestionListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            QuizQuestionListWrapper wrapper = new QuizQuestionListWrapper();
            wrapper.setQuizQuestions(quizQuestions);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setQuestionFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Dialogs.create().title("Error")
                    .masthead("Could not save data to file:\n" + file.getPath())
                    .showException(e);
        }
    }
    
    /**
     * returns the variable that defines if admin mode is activeated or not.
     * @return
     */
	public boolean isAdminMode() {
		return adminMode;
	}
	/**
	 * This sets the Admin mode either on (true) or off (false) - Admin mode give the users more options 
	 * @param isAdminMode
	 */
	public void setAdminMode(boolean isAdminMode) {
		
		adminMode = isAdminMode;
		quoteOverviewController.setAdminMode(isAdminMode);
		rootLayoutController.setAdminMode(isAdminMode);
		resetChoicesQuestionsAndAnswers();
	}
	/**
	 * This resets all of the answers give by a user.
	 */
	public void resetChoicesQuestionsAndAnswers() {
		// All answers and questions will revert to "Not Answered" 
		for(QuizQuestion question: quizQuestions){
			question.setQuestionAnswered(false);
			for(QuizAns answer: question.getAnswers()){
				answer.setAnswerChosen(false); 
			}
		}
	}
	
	/**
	 * If all questions are answered this will return true.
	 * @return
	 */
	public boolean areAllQustionsAnswered()
	{
		//goes through the questions until it finds one that has no been answered.
		for(QuizQuestion question: quizQuestions){
			if(question.isQuestionAnswered() == false)
				return false;
		}
		
		return true;
	}
	/**
	 * For a non-Admin user this method finds the next unanswered question and displays it
	 */
	public void loadNextQuestion()
	{
		if(isAdminMode())
			return;
		for(QuizQuestion question: quizQuestions){
			if(question.isQuestionAnswered() == false)
				quoteOverviewController.setQuestion(question);
		}
	}


}
