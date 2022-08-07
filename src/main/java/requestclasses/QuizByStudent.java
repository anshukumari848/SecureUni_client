package requestclasses;

import Utilities.ServerRequest;

import java.io.Serializable;

public class QuizByStudent implements Serializable {
    private String studentID;

    public QuizByStudent(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    @Override
    public String toString() {
        return String.valueOf(ServerRequest.QUIZ_BY_STUDENT_REQUEST);
    }
}
