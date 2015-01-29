package nd.create.quote.view;

import nd.create.quote.model.QuizAns;
import nd.create.quote.model.QuizQuestion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.stage.Stage;

public class ChartOfVictoryDialogController {

	private Stage dialogStage;
	
	@FXML
	PieChart chart;
	
	@FXML
	ObservableList<Data> pieChartData;
	
	public ChartOfVictoryDialogController() {
		// TODO Auto-generated constructor stub
	}
	

	 public void initialize(ObservableList<QuizQuestion> quizQuestions) {
		 
		 int correctNumber = 0;
		 int wrongNumber = 0;
		 
		 for(QuizQuestion question: quizQuestions)
		 {
			 for(QuizAns answer: question.getAnswers())
			 {
				 if(answer.isAnswerChosen())
				 {
					 if(answer.isCorrect())
					 {
						 correctNumber++;
					 }else{
						 wrongNumber++;
					 }
				 }
			 }
		 }
		 
		 
		 //pieChartData = FXCollections.observableArrayList();
		 pieChartData = FXCollections.observableArrayList(
	                new PieChart.Data("Correct", correctNumber),
	                new PieChart.Data("Wrong", wrongNumber));
		 
		 chart.setData(pieChartData);
		 //chart.setTitle("How you answered");
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
	  public void handleCancel()
	  {
		  dialogStage.close();
	  }

}
