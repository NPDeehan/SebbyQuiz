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
	
	public void addQuestionToList(QuizQuestion question)
	{
		quizQuestions.add(question);
	}
	
    public Stage getPrimaryStage() {
        return primaryStage;
    }

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
     * Returns the data as an observable list of Persons. 
     * @return
     */
    public ObservableList<QuizQuestion> getQuestionData() {
        return quizQuestions;
    }
    
	
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
	

	 private void showQuoteOverview() {
	    	try {
	            // Load person overview.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("view/QuoteOverview.fxml"));
	            AnchorPane personOverview = (AnchorPane) loader.load();

	            // Set person overview into the center of root layout.
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

	            // Set the persons into the controller.
	            AboutDialogController controller = loader.getController();
	            controller.setDialogStage(dialogStage);

	            Image oddImage = new Image("file:resources/images/J8oocez.gif");
	            
	            controller.setupImage(oddImage);
	            controller.setMainApp(this);
	            dialogStage.show();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    /**
	     * Opens a dialog to show ABOUT
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

	            // Set the persons into the controller.
	            CreateQuestionController controller = loader.getController();
	            controller.setDialogStage(dialogStage);
	            controller.setMainApp(this);
	            
	            dialogStage.show();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
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
	
	            // Set the persons into the controller.
	            ChartOfVictoryDialogController controller = loader.getController();
	            controller.setDialogStage(dialogStage);
	            controller.initialize(quizQuestions);
	           // controller.setMainApp(this);
	            
	            dialogStage.show();
	    	  } catch (IOException e) {
		            e.printStackTrace();
		      }
	    }
	    
	    
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
     * person file.
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
    
    public Image getIncorrectImage()
    {
    	Image IncorrectImage = new Image("file:resources/images/Wrong2.png");
    	
    	return IncorrectImage;
    }
    public Image getCorrectImage()
    {
    	return new Image("file:resources/images/Correct3.png");
    }
    
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

        } catch (Exception e) { // catches ANY exception
            Dialogs.create()
                    .title("Error")
                    .masthead("Could not load data from file:\n" + file.getPath())
                    .showException(e);
        }
    }
    
    /**
     * Returns the person file preference, i.e. the file that was last opened.
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
    

	public boolean isAdminMode() {
		return adminMode;
	}

	public void setAdminMode(boolean isAdminMode) {
		
		adminMode = isAdminMode;
		quoteOverviewController.setAdminMode(isAdminMode);
		rootLayoutController.setAdminMode(isAdminMode);
		resetChoicesQuestionsAndAnswers();
	}
	
	public void resetChoicesQuestionsAndAnswers() {
		
		for(QuizQuestion question: quizQuestions){
			question.setQuestionAnswered(false);
			for(QuizAns answer: question.getAnswers()){
				answer.setAnswerChosen(false);
			}
		}
	}
	
	
	public boolean areAllQustionsAnswered()
	{
		for(QuizQuestion question: quizQuestions){
			if(question.isQuestionAnswered() == false)
				return false;
		}
		
		return true;
		
		
	}
	
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
