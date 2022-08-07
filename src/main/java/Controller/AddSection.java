package Controller;

import Utilities.Status;
import classes.Quiz;
import classes.Subject;
import classes.UidGenerator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainclass.Main;
import requestclasses.QuizListFetchRequest;
import requestclasses.SectionsAddRequest;
import requestclasses.SubjectListFetchRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class AddSection {
    @FXML
    Button back;
    @FXML
    ComboBox subject, quiz;
    @FXML
    TextField sectionname, sectiontime;
    @FXML
    Label status;
    volatile List<Subject> serversubjects;
    volatile List<Quiz> serverquiz;

    @FXML
    TextField searchsubject;
    volatile String check;

    //    initialises the scene.
    public void initialize() {
        quiz.setVisible(false);
        SubjectListFetchRequest subjectListFetchRequest = new SubjectListFetchRequest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {


                    Socket socket = Main.teachersocket;

                    ObjectOutputStream oos = Main.teacherOutputStream;
                    oos.writeObject(subjectListFetchRequest);
                    oos.flush();
                    ObjectInputStream ois = Main.teacherInputStream;
                    serversubjects = (List<Subject>) ois.readObject();


                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            subject.getItems().addAll(serversubjects);
                        }
                    });


                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }).start();


    }

    //    back to teacher portal
    public void onbackclicked() {
        Stage primaryStage = (Stage) back.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("/teacherportal.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }

    //    add the section and send the server a section add request.
    public void onsubmitclicked(ActionEvent actionEvent) {
        if (sectionname.getText().isEmpty() || sectiontime.getText().isEmpty()) {
            status.setText("Enter both fields");
            return;
        }
        SectionsAddRequest sectionsAddRequest = new SectionsAddRequest();
        Quiz quiz = (Quiz) this.quiz.getSelectionModel().getSelectedItem();
        sectionsAddRequest.setExamID(quiz.getExamID());
        sectionsAddRequest.setSectionID(UidGenerator.generateuid());
        sectionsAddRequest.setSectionName(sectionname.getText());
        int t = Integer.parseInt(sectiontime.getText());  // dekhna

        sectionsAddRequest.setSectionTime(t);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {


                    Socket socket = Main.teachersocket;

                    ObjectOutputStream oos = Main.teacherOutputStream;
                    oos.writeObject(sectionsAddRequest);
                    oos.flush();
                    ObjectInputStream ois = Main.teacherInputStream;
                    check = (String) ois.readObject();
                    if (check.equals(String.valueOf(Status.SUCCESS))) {

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                onbackclicked();
                            }
                        });
                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                status.setText("Error");
                            }
                        });
                    }


                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }).start();


    }

    //    fetches a list of sections corresponding to a subject.
    public void onsubjectclicked() {
        quiz.setVisible(true);
        quiz.getItems().clear();
//        if(quiz.getSelectionModel().isEmpty()){
//            status.setText("Select quiz");
//            return;
//        }
        System.out.println("quiz list show");
        QuizListFetchRequest quizListFetchRequest = new QuizListFetchRequest();
        quizListFetchRequest.setTeacherID(Main.teacher.getTeacherID());
        Subject currentSubject = (Subject) subject.getSelectionModel().getSelectedItem();
        quizListFetchRequest.setSubjectID(currentSubject.getSubjectID());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {


                    Socket socket = Main.teachersocket;

                    ObjectOutputStream oos = Main.teacherOutputStream;
                    System.out.println("hi");
                    oos.writeObject(quizListFetchRequest);
                    oos.flush();
                    ObjectInputStream ois = Main.teacherInputStream;
                    serverquiz = (List<Quiz>) ois.readObject();


                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            quiz.getItems().addAll(serverquiz);
                        }
                    });


                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }).start();


    }
    public void onquizclicked(ActionEvent actionEvent) {
//        section.setVisible(true);
//        section.getItems().clear();
//        Quiz selectedQuiz = (Quiz) quiz.getSelectionModel().getSelectedItem();
//        SectionFetchRequest sectionFetchRequest = new SectionFetchRequest(selectedQuiz.getExamID());


    }

}
