package Controller;

import Utilities.Status;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mainclass.Main;
import questions.Question;
import requestclasses.ScoreAddRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class Quizover {
    private int total=0;
    volatile int score = 0;
    @FXML
    Label scorelabel,status;

    volatile String check;

    public void scorebutton(ActionEvent actionEvent) {
        total =Student_portal.decryptedunsortedQuestions.size();
        for(Map.Entry<String,Integer>entry : Main.submitedAswers.entrySet()){
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key);
            System.out.println(value);

            for(Question q : Student_portal.decryptedunsortedQuestions){
                if(q.getQuestionID().equals(key)&&q.getCorrectoption()==value){

                    score++;
                }
            }
        }
        scorelabel.setText(score+" out of "+total);
        Main.submitedAswers.clear();

    }

    public void onsubmitclicked(ActionEvent actionEvent) {
        if(scorelabel.getText().isEmpty()){
            status.setText("Calculate score");
            return;
        }
        ScoreAddRequest scoreAddRequest = new ScoreAddRequest(Student_portal.selectedQuiz.getExamID(),(double)score);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{


                    Socket socket = Main.studentSocket;
                    try {
                        ObjectOutputStream oos = Main.studentOutputStream;

                        ObjectInputStream ois = Main.studentInputStream;

                        oos.writeObject(scoreAddRequest);
                        check = (String) ois.readObject();
//                        creating a new socket if their is a connection breakdown..

                        }catch (IOException g){
                            g.printStackTrace();
                            System.out.println("Server Connection Unavailable");
                        }


                        if(check.equals(String.valueOf(Status.SUCCESS))) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Stage primaryStage = (Stage) scorelabel.getScene().getWindow();
                                    Parent root = null;
                                    try {

                                        root = FXMLLoader.load(getClass().getResource("/studentportal.fxml"));
                                    } catch (IOException e) {
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
                        e.printStackTrace();
                    }
                }
            }).start();



        }
    }



