package Controller;

import Utilities.Status;
import classes.FileHandle;
import classes.FileReciever;
import classes.Quiz;
import classes.Subject;
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
import questions.Question;
import requestclasses.QuestionFetchRequest;
import requestclasses.QuizListFetchStudent;
import requestclasses.SubjectListFetchRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.List;

public class Student_portal {
    @FXML
    Button logout;
    @FXML
    Label status;
    @FXML
    ListView subjects,quizs;
    volatile List<Subject> serversubjects;
    volatile List<Quiz> serverquiz;
    static List<Question> decryptedunsortedQuestions;
    static Quiz selectedQuiz;
    @FXML
    TextArea info;
    volatile String check;

    public void initialize(){
        subjects.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        quizs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        SubjectListFetchRequest subjectListFetchRequest =new SubjectListFetchRequest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{


                    Socket socket = Main.studentSocket;

                    ObjectOutputStream oos = Main.studentOutputStream;
                    oos.writeObject(subjectListFetchRequest);
                    oos.flush();
                    ObjectInputStream ois = Main.studentInputStream;
                    serversubjects = (List<Subject>) ois.readObject();


                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            subjects.getItems().addAll(serversubjects);

                        }
                    });

                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }).start();


    }
    public void onlogoutclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) logout .getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("/studentlogin.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }

    public void onstartquizclicked(ActionEvent actionEvent) {

        Quiz quiz = (Quiz)quizs.getSelectionModel().getSelectedItem();
        selectedQuiz=quiz;
        QuestionFetchRequest questionFetchRequest = new QuestionFetchRequest(quiz.getExamID());
        questionFetchRequest.setStudentID(Main.student.getStudentID());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{


                    Socket socket = Main.studentSocket;

                    ObjectOutputStream oos = Main.studentOutputStream;
                    oos.writeObject(questionFetchRequest);
                    oos.flush();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            status.setText("Please wait.");
                        }
                    });

                    FileReciever fileReciever = new FileReciever();
                    SocketChannel socketChannel = fileReciever.createServerSocketChannel();
                    System.out.println("Socket channel created...");
                    fileReciever.readFileFromSocket(socketChannel,quiz.getExamID());
                    System.out.println("Reading files..");
                    List<Question> questions = FileHandle.fileRead(quiz.getExamID());
                    decryptedunsortedQuestions = questions;
                    ObjectInputStream ois = Main.studentInputStream;
                    check = (String)ois.readObject();
                    if(check.equals(String.valueOf(Status.SUCCESS))) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                status.setText("Success");
                                Stage primaryStage = (Stage) status.getScene().getWindow();
                                Parent root = null;
                                try {
//doubt
                                    root = FXMLLoader.load(getClass().getResource("/quiz.fxml"));
                                }catch(IOException e){
                                    e.printStackTrace();
                                }
                                primaryStage.setScene(new Scene(root, 1303, 961));
                            }
                        });
                    }else{
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                status.setText("Error");
                            }
                        });
                    }
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }).start();


    }

    public void ondashboardclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) status.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("/studentdashboard.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));


    }
    private String quizinfo(Quiz quiz){
        String temp = "Quiz Info:"+"\n";
        temp+= "ExamName: "+quiz.getExamName()+"\n";
        temp+="TeacherId: "+quiz.getTeacherID()+"\n";
        return temp;
    }




    public void handleclickedsubject(MouseEvent mouseEvent) {
        Subject currentSubject = (Subject)subjects.getSelectionModel().getSelectedItem();
        QuizListFetchStudent quizListFetchRequest =new QuizListFetchStudent(Main.student.getStudentID(),currentSubject.getSubjectID());
        quizListFetchRequest.setStudentID(Main.student.getStudentID());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{


                    Socket socket = Main.studentSocket;

                    ObjectOutputStream oos = Main.studentOutputStream;
                    oos.writeObject(quizListFetchRequest);
                    oos.flush();
                    ObjectInputStream ois = Main.studentInputStream;
                    serverquiz = (List<Quiz>) ois.readObject();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            quizs.getItems().clear();
                            for (Quiz quiz:serverquiz){
                                System.out.println(quiz.getExamName());
                            }
                            quizs.getItems().addAll(serverquiz);
                        }
                    });

                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }).start();
    }

    public void handleclickedquiz(MouseEvent mouseEvent) {
        Quiz quiz = (Quiz)quizs.getSelectionModel().getSelectedItem();
        String in = quizinfo(quiz);
        info.setText(in);
    }
}
