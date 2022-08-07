package Controller;

import Utilities.Status;
import classes.Comment;
import classes.Quiz;
import classes.UidGenerator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainclass.Main;
import requestclasses.CommentAddRequest;
import requestclasses.QuizListFetchTeacher;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class TeacherComment {
    @FXML
    Label status;
    @FXML
    TextField comment;
    @FXML
    TextArea commentinfo;
    @FXML
    ListView quizs;
    volatile List<Quiz> serverquizs;
    volatile String check;
    volatile List<Comment> serverComments;

    public void initialize(){

        QuizListFetchTeacher quizListFetchTeacherRequest = new QuizListFetchTeacher(Main.teacher.getTeacherID());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{


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

                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }).start();

    }
    public void onsubmitclicked(ActionEvent actionEvent) {

                            if(comment.getText().isEmpty()){
                                status.setText("Enter a comment");
                                return;
                            }
                            Quiz quiz = (Quiz)quizs.getSelectionModel().getSelectedItem();

                            Comment commentobj =new Comment(quiz.getExamID(), UidGenerator.generateuid(),Main.teacher.getTeacherID());
                            commentobj.setComment(comment.getText());
                            commentobj.setPosterName(Main.teacher.getFirstName());
                            CommentAddRequest commentAddRequest = new CommentAddRequest(commentobj);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try{
                                        Socket socket = Main.teachersocket;

                                        ObjectOutputStream oos = Main.teacherOutputStream;
                                        oos.writeObject(commentAddRequest);
                                        oos.flush();
                                        ObjectInputStream ois = Main.teacherInputStream;
                                        check = (String) ois.readObject();

                                        if(check.equals(String.valueOf(Status.SUCCESS))) {
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Stage primaryStage = (Stage) status.getScene().getWindow();
                                                    Parent root = null;
                                                    try {

                                                        root = FXMLLoader.load(getClass().getResource("/teacherdashboard.fxml"));
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


                                    }

    public void onbackclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) status.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("/teacherdashboard.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }

    private String commentinformation(List<Comment> li){
        String temp = "";
        for(Comment comment : li){
            temp+= comment.getPosterName()+" : "+comment.getComment()+"\n";
        }
        return temp;
    }

}

