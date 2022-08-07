package Controller;

import Utilities.Status;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import classes.HashGenerator;
import classes.Teacher;
import mainclass.Main;
import requestclasses.TeacherLoginRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static mainclass.Main.teacher;


public class Teacher_login {

    @FXML
    TextField email;
    @FXML
    Button signup, login;
    @FXML
    PasswordField password;
    @FXML
    Label status;

    public void onsignupclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) signup.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/teachersignup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 800, 600));

    }

    public void onloginclicked(ActionEvent actionEvent) {
        if (email.getText().isEmpty() || password.getText().isEmpty()) {
            status.setText("Enter both fields");
            return;
        }
        TeacherLoginRequest teacherLoginRequest = new TeacherLoginRequest(email.getText(), HashGenerator.hash(password.getText()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {


                    Socket socket = new Socket(Main.serverip, Main.portno);

                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(teacherLoginRequest);
                    oos.flush();
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    teacher = (Teacher) ois.readObject();

                    if (teacher.getTeacherLoginStatus().equals(String.valueOf(Status.SUCCESS))) {
                       teacher = teacher;
                        Main.teachersocket = socket;
                        Main.teacherInputStream = ois;
                        Main.teacherOutputStream = oos;

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Stage primaryStage = (Stage) login.getScene().getWindow();
                                Parent root = null;
                                try {

                                    root = FXMLLoader.load(getClass().getResource("/teacherportal.fxml"));
                                } catch (IOException e) {

                                    e.printStackTrace();
                                }
                                primaryStage.setScene(new Scene(root, 1081, 826));
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
}
