package Controller;

import classes.Quiz;
import classes.Student;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mainclass.Main;
import requestclasses.QuizByStudent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class StudentDashBoard {
    public TextArea quizscore;
    @FXML
    Label status;
    @FXML
    ListView quizs;
    volatile List<Student> serverStudents;
    volatile double quizScore;
    volatile List<Quiz> serverquizs;

    public void initialize(){
        quizs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        get a list of quizs given by the current student.
        QuizByStudent quizByStudentRequest = new QuizByStudent(Main.student.getStudentID());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{


                    Socket socket = Main.studentSocket;

                    ObjectOutputStream oos = Main.studentOutputStream;
                    oos.writeObject(quizByStudentRequest);
                    oos.flush();
                    ObjectInputStream ois = Main.studentInputStream;
                    serverquizs = (List<Quiz>) ois.readObject();


                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            quizs.getItems().addAll(serverquizs);

                        }
                    });

                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }).start();

    }

    public void onbackclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) status.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("/studentportal.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1303, 961));

    }

    public void oncommentclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) status.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("/studentcomment.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));

    }


    public void handleclickedquiz(MouseEvent mouseEvent) {
    }
}
