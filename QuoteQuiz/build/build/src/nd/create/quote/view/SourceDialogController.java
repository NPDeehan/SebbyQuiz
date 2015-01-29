package nd.create.quote.view;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.validator.routines.UrlValidator;
import org.controlsfx.dialog.Dialogs;

import nd.create.quote.model.QuizAns;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SourceDialogController {
	
	private QuizAns quizAns;

	public SourceDialogController() {
		
	}
	
	private Stage dialogStage;
	@FXML
	private Button okButton;
	@FXML
	private Button cancellButton;
	
	@FXML
	private TextField commentField;
	@FXML
	private TextField sourceURLField;
	
	@FXML
	private void initialize(){
		
	}
	
	
	 public void initializeData(QuizAns quizAnswer) {
		 
		 quizAns = quizAnswer;
		 commentField.setText(quizAns.getResponse());
		 if(quizAns.getSourceURL() != null)
			 sourceURLField.setText(quizAns.getSourceURL().toString());

	 }
	 
	  /**
		  * Sets the stage of this dialog.
		  * 
		  * @param dialogStage
	  */
	  public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	   }
	  
	  @FXML
	  public void handleOK()
	  {
		  	
		    quizAns.setResponse(commentField.getText());
		  
		  	String url = sourceURLField.getText();
		  	if(url !="")
		  	{
			    if(!url.startsWith("http://"))
			    	url = "http://"+url;
			    UrlValidator urlValidator = new UrlValidator();
			    boolean isValidURL = urlValidator.isValid(url);

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
						e.printStackTrace();
					}
			    }
		  	}
		  
		  dialogStage.close();
	  }
	  @FXML
	  public void handleCancel()
	  {
		  dialogStage.close();
	  }

}
