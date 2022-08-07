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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import classes.UidGenerator;
import mainclass.Main;
import requestclasses.SubjectAddRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AddSubject {
    @FXML
    Button back,submit;
    @FXML
    TextField name;
    @FXML
    Label status;
    volatile String check;

    public void onsubmitclicked(ActionEvent actionEvent) {
        if(name.getText().isEmpty()){
            status.setText("Enter Subject Name");
            return ;
        }

        SubjectAddRequest sar = new SubjectAddRequest(name.getText(),Main.teacher.getTeacherID(), UidGenerator.generateuid());
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    Socket socket = Main.teacherSocket;
                    ObjectOutputStream oos = Main.teacherOutputStream;
                    oos.writeObject(sar);
                    oos.flush();
                    ObjectInputStream ois= Main.teacherInputStream;
                    check=(String)ois.readObject();
                    System.out.println(check);

                    if(check.equals(String.valueOf(Status.SUCCESS))){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run()
                            {
                                Stage primaryStage = (Stage) back.getScene().getWindow();
                                Parent root = null;
                                try{
                                    root= FXMLLoader.load(getClass().getResource("/teacherportal.fxml"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                primaryStage.setScene( new Scene(root,800,600));
                            }

                        });
                    }
                    else{
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                status.setText("Error");
                            }
                        });
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void onbackclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) back.getScene().getWindow();
        Parent root = null;
        try{
            root= FXMLLoader.load(getClass().getResource("/teacherportal.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene( new Scene(root,800,600));
    }




}
