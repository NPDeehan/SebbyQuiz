package nd.create.quote.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import nd.create.quote.MainApp;

public class RootLayoutController {
	
	private MainApp mainApp;
	
	@FXML
	private MenuItem createQuestionMenuItem;
	
	@FXML 
	private MenuItem creatNewQuestionFile;
	
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        setAdminMode(mainApp.isAdminMode());
    }
    
    @FXML
    private void handleAbout() {
    	mainApp.showAbout();
    }
    
    @FXML
    private void handleCreateQuestions() {
    	mainApp.showCreateQuestion();
    }
    
    
    @FXML
    private void handleSave() {
    	System.out.println("Starting to Save...");
        File personFile = mainApp.getQuestionFilePath();
        if (personFile != null) {
            mainApp.saveQuestionDataToFile(personFile);
        } else {
        	handleSaveAs();
        }
    }
    
    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
    	System.out.println("Going to Save As...");
    	
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveQuestionDataToFile(file);
        }
    }
    
    @FXML
    private void handleCreateNewFile() {
    	mainApp.getQuestionData().clear();
    	mainApp.setQuestionFilePath(null);	
    }
    
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadQuestionDataFromFile(file);
        }
    }
   
    public void setAdminMode(boolean adminMode)
    {
    	createQuestionMenuItem.setVisible(adminMode);
    	creatNewQuestionFile.setVisible(adminMode);
    }


    @FXML
    private void handelClose(){
    	System.exit(0);
    }

}
