package mainclass;

import classes.Student;
import classes.Teacher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class Main extends Application {
    public static Socket studentSocket,teacherSocket;
    public static ObjectOutputStream studentOutputStream ,teacherOutputStream;
    public static ObjectInputStream studentInputStream ,teacherInputStream;
    public static OutputStream os;
    public static InputStream is;
    public static  String serverip="localhost";
    public static int portno =6963;
    public static Student student;
    public static Teacher teacher;
    public static List<String> questionType = new ArrayList<>();
    public static HashMap<String,Integer> submitedAswers= new HashMap<>();
    public static Socket teachersocket;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/studentlogin.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("SecureQuiz");
        primaryStage.setScene(new Scene(root, 800 , 600));

        primaryStage.show();
    }
}



