package Controller;

import Utilities.LoginStatus;
import com.sun.org.apache.xerces.internal.impl.xs.XSComplexTypeDecl;
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
import mainclass.Main;
import requestclasses.StudentLoginRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Student_login {

    @FXML
    TextField email;
    @FXML
    Button teacher,login,studentsignupfx;
    @FXML
    PasswordField password;
    @FXML
    Label status;
    volatile Student check;
    public void onstudentsignupclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) studentsignupfx.getScene().getWindow();
        Parent root = null;
        try{
            root= FXMLLoader.load(getClass().getResource("/studentsignup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene( new Scene(root,800,600));

    }

    public void onteacherclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) teacher.getScene().getWindow();
        Parent root = null;
        try{
            root= FXMLLoader.load(getClass().getResource("/teacherlogin.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene( new Scene(root,800,600));
    }


    public void onloginaction(ActionEvent actionEvent) {

        StudentLoginRequest slr = new StudentLoginRequest(email.getText(), HashGenerator.hash(password.getText()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket = new Socket(Main.serverip,Main.portno);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());// connection to server
                    oos.writeObject(slr);
                    oos.flush();
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    check = (Student)ois.readObject();
                    if(check.getStudentLoginStatus().equals(String.valueOf(LoginStatus.SUCCESS))){
                        Main.studentSocket=socket;
                        Main.student=check;
                        Main.studentInputStream=ois;
                        Main.studentOutputStream=oos;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Stage primaryStage = (Stage) teacher.getScene().getWindow();
                                Parent root = null;
                                try {

                                    root = FXMLLoader.load(getClass().getResource("/studentportal.fxml"));
                                }catch(IOException e){
                                    e.printStackTrace();
                                }
                                primaryStage.setScene(new Scene(root, 1303, 961));
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
