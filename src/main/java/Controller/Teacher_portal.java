package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Teacher_portal {

    @FXML
    Button subject,question,quiz;
    @FXML
    Button dashboard,section,logout;


    public void onlogoutaction(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) logout.getScene().getWindow();
        Parent root = null;
        try{
            root= FXMLLoader.load(getClass().getResource("/teacherlogin.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene( new Scene(root,800,600));
    }

    public void onsectionaction(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) section.getScene().getWindow();
        Parent root = null;
        try{
            root= FXMLLoader.load(getClass().getResource("/addsection.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene( new Scene(root,800,600));
    }

    public void ondashboardaction(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) dashboard.getScene().getWindow();
        Parent root = null;
        try{
            root= FXMLLoader.load(getClass().getResource("/teacherdashboard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene( new Scene(root,800,600));
    }

    public void onaddquiz(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) quiz.getScene().getWindow();
        Parent root = null;
        try{
            root= FXMLLoader.load(getClass().getResource("/addquiz.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene( new Scene(root,800,600));
    }

    public void onaddsubjectaction(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) subject.getScene().getWindow();
        Parent root = null;
        try{
            root= FXMLLoader.load(getClass().getResource("/addsubject.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene( new Scene(root,800,600));
    }

    public void onaddquestionaction(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) question.getScene().getWindow();
        Parent root = null;
        try{
            root= FXMLLoader.load(getClass().getResource("/Addquestion.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene( new Scene(root,800,600));
    }
}
