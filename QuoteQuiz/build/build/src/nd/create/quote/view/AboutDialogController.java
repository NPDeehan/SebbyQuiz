package nd.create.quote.view;

import nd.create.quote.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class AboutDialogController {

	public AboutDialogController() {
		// TODO Auto-generated constructor stub
	}
	
	private Stage dialogStage;
	@FXML
	private Button thatWasOdd;
	@FXML
	private ImageView oddImage;
	
	private MainApp mainApp;
	
		
	 @FXML
	 private void initialize() {
		 

	 }
	 
	  /**
		  * Sets the stage of this dialog.
		  * 
		  * @param dialogStage
	  */
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
	  public void handleAdminMode()
	  {
		  if(mainApp.isAdminMode()){
			  mainApp.setAdminMode(false);
		  }else{
			  mainApp.setAdminMode(true);
		  }
		  
		  dialogStage.close();
	  }
	  
	  @FXML
	  public void handleThatWasOdd()
	  {
		  dialogStage.close();
	  }
	  
	  public void setupImage(Image image)
	  {
		  oddImage.setImage(image);
	  }


}
