package Controller;

import classes.Quiz;
import classes.Student;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mainclass.Main;
import requestclasses.QuizListFetchTeacher;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class TeacherDashBoard {

    public TextArea quizscore ;
    @FXML
    Label status;
    @FXML
    ListView quizs;
    @FXML
    Button back, comment;
    volatile List<Student> serverStudents;
    volatile double quizScore;
    volatile List<Quiz> serverquizs;

    public void initialize() {
        quizs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        get a list of quizs given by the current student.
        QuizListFetchTeacher quizListFetchTeacherRequest = new QuizListFetchTeacher(Main.teacher.getTeacherID());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {


                    Socket socket = Main.teachersocket;

                    ObjectOutputStream oos = Main.teacherOutputStream;
                    oos.writeObject(quizListFetchTeacherRequest);
                    oos.flush();
                    ObjectInputStream ois = Main.teacherInputStream;
                    serverquizs = (List<Quiz>) ois.readObject();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            quizs.getItems().addAll(serverquizs);

                        }
                    });


                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }).start();

    }

    public void onbackclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) back.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("/teacherportal.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));

    }

    public void oncommentclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) back.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("/teachercomment.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }



    public void handleclickedquiz(MouseEvent mouseEvent) {
    }

    public void handleclickedstudents(MouseEvent mouseEvent) {
    }
}


