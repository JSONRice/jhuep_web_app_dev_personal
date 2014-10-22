package hw7.main;

//FXOptionPane.class
import javafx.stage.*; 
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class FXOptionPane {
  Button btnYes, btnNo;
  public FXOptionPane(final Stage stg) {
      btnYes = new Button();
       btnNo = new Button();
       
  final  Stage stage = new Stage();     
      stage.initStyle(StageStyle.UNDECORATED);
      
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.initOwner(stg);
      stage.setTitle("FXOptionPane");
      Group root = new Group();
      Scene scene = new Scene(root, 220, 100, Color.LIGHTGRAY);
      root.setStyle("-fx-border:black");
      
       
       btnYes.setOnAction(new EventHandler<ActionEvent>() {

          public void handle(ActionEvent event) {
              
           //to do 
           stage.hide();
           
           
              
          }
      });
       
       
       
       btnNo.setOnAction(new EventHandler<ActionEvent>() {

          public void handle(ActionEvent event) {
              
              
          // to do    
           stage.hide();
           
           
              
          }
      });
       
       
      btnYes.setLayoutX(80);
      btnYes.setLayoutY(40);
      btnYes.setText("Yes");
      
      
       btnNo.setLayoutX(125);
      btnNo.setLayoutY(40);
      btnNo.setText("No");
     
      root.getChildren().addAll(btnYes, btnNo);   
      stage.setScene(scene);        
      stage.show();

  
  }
  
}