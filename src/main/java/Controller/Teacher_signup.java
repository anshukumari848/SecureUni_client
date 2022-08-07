package Controller;

import Utilities.Status;
import Utilities.UserJob;
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
import classes.UidGenerator;
import mainclass.Main;
import requestclasses.SignUpRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Teacher_signup {
    @FXML
    TextField fname,lastname;
    @FXML
    TextField email;
    @FXML
    Button back,submit;
    @FXML
    PasswordField password;
    @FXML
    Label status;
    volatile String check;
    public void onback(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) back.getScene().getWindow();
        Parent root = null;
        try{
            root= FXMLLoader.load(getClass().getResource("/studentlogin.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene( new Scene(root,800,600));
    }

    public void onsubmit(ActionEvent actionEvent) {

        Teacher teacher = new Teacher(UidGenerator.generateuid(),UidGenerator.generateuid(email.getText()));
        teacher.setEmail(email.getText());
        teacher.setFirstName(fname.getText());
        teacher.setLastName(lastname.getText());
        teacher.setJob(String.valueOf(UserJob.TEACHER));
        SignUpRequest signUpRequest = new SignUpRequest(teacher, HashGenerator.hash(password.getText()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{


                    Socket socket = new Socket(Main.serverip,Main.portno);

                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(signUpRequest);
                    oos.flush();
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    check = (String)ois.readObject();
                    if(check.equals(String.valueOf(Status.SUCCESS))){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Stage primaryStage = (Stage) submit.getScene().getWindow();
                                Parent root = null;
                                try {

                                    root = FXMLLoader.load(getClass().getResource("/teacherlogin.fxml"));
                                }catch(IOException e){
                                    e.printStackTrace();
                                }
                                primaryStage.setScene(new Scene(root, 1081, 826));
                            }
                        });

                    }else {
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

}
