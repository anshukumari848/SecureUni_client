package Controller;

import Utilities.Status;
import classes.FileHandle;
import classes.FileSender;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mainclass.Main;
import questions.Question;
import requestclasses.QuizAddRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class Cont_List {

    public ListView questions;
    public TextArea questiondescription;

    @FXML
    Label status;
    @FXML
    Button back;
    volatile String check;

    public void initialize(){
        questions.getItems().addAll(AddQuestion.questionList);

    }
    public void onbackclicked(ActionEvent actionEvent) {

        Stage primaryStage = (Stage) back.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("/Addquestion.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));


    }

    public void onsubmitclicked(ActionEvent actionEvent) {
        FileHandle.fileWrite( AddQuestion.questionList,AddQuestion.quizid);  // perform encrypted
        QuizAddRequest quizAddRequest = new QuizAddRequest(AddQuestion.quizid);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{


                    Socket socket = Main.teachersocket;

                    ObjectOutputStream oos = Main.teacherOutputStream;
                    oos.writeObject(quizAddRequest);
                    oos.flush();
                    ObjectInputStream ois = Main.teacherInputStream;
                    check = (String) ois.readObject();
                    if(check.equals(String.valueOf(Status.SUCCESS))){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                Stage primaryStage = (Stage) back.getScene().getWindow();
                                Parent root = null;
                                try {

                                    root = FXMLLoader.load(getClass().getResource("/teacherportal.fxml"));
                                }catch(IOException e){
                                    e.printStackTrace();
                                }
                                primaryStage.setScene(new Scene(root, 1081, 826));
                            }
                        });
                    }

                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }).start();
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            System.out.println(e);
        }
        FileSender fileSender = new FileSender();
        FileSender nioClient = new FileSender();
        SocketChannel socketChannel = nioClient.createChannel();
        nioClient.sendFile(socketChannel,AddQuestion.quizid);

    }

    public void onremoveclicked(ActionEvent actionEvent) {
        Question selectedQuestion = (Question)questions.getSelectionModel().getSelectedItem(); // select  question which we want to delete
        AddQuestion.questionList.remove(selectedQuestion);  // list ki ye question h which we want to delete
        questions.getItems().clear();
        questions.getItems().addAll(AddQuestion.questionList);

    }


    public void handleclickedquestions(MouseEvent mouseEvent) {
    }
}
