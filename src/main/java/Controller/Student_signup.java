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
import classes.Student;
import classes.UidGenerator;
import mainclass.Main;
import requestclasses.SignUpRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Student_signup {
    @FXML
     TextField firstname,lastname;
    @FXML
    TextField email;
    @FXML
    Button back,submit;
    @FXML
    PasswordField password;
    @FXML
    Label status;

    volatile String check;
    Student student;
    SignUpRequest signUpRequest;   // reguest class

    //   signup for student
    public void onsubmitaction(ActionEvent actionEvent) {
       if(firstname.getText().isEmpty() || lastname.getText().isEmpty() || email.getText().isEmpty() || password.getText().isEmpty())
       {
           status.setText("Enter all Details");
           return;
       }

        String string= UidGenerator.generateuid();
        System.out.println(string);
        student = new Student(string, UidGenerator.generateuid(email.getText()));
        System.out.println(student.getUserID());
        student.setEmail(email.getText());

        student.setFirstName(firstname.getText());
        student.setLastName(lastname.getText());
        student.setJob(String.valueOf(UserJob.STUDENT));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    SignUpRequest signUpRequest = new SignUpRequest(student, HashGenerator.hash(password.getText())); // call constructor

                    Socket socket = new Socket(Main.serverip,Main.portno);

                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); // it create a pipe and send socket object
                    oos.writeObject(signUpRequest); // send the student
                    oos.flush();
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream()); // pipe create to receive data
                    check = (String)ois.readObject(); // read

                    // check the data send by server == client
                    if(check.equals(String.valueOf(Status.SUCCESS))){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Stage primaryStage = (Stage) submit.getScene().getWindow();
                                Parent root = null;
                                try {

                                    root = FXMLLoader.load(getClass().getResource("/studentlogin.fxml"));
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
//    back to login scene.
  public void onbackaction(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) back.getScene().getWindow();
        Parent root = null;
        try{
            root= FXMLLoader.load(getClass().getResource("/studentlogin.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene( new Scene(root,800,600));
    }
}
