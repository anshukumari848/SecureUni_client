package classes;

import java.io.Serializable;

public class Student extends User implements Serializable {

    private String studentID;//Unique ID of a Student
    private String studentLoginStatus;

    public Student(String userID,String studentID) {
        super();
        this.setUserID(userID);
        this.studentID = studentID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentLoginStatus() {
        return studentLoginStatus;
    }

    public void setStudentLoginStatus(String studentLoginStatus) {
        this.studentLoginStatus = studentLoginStatus;
    }

    @Override
    public String toString() {
        return this.getFirstName();
    }
}
