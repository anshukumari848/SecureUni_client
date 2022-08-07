package requestclasses;

import Utilities.ServerRequest;

import java.io.Serializable;

public class QuizListFetchTeacher implements Serializable {
    private String teacherID;

    public QuizListFetchTeacher(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    @Override
    public String toString() {
        return String.valueOf(ServerRequest.QUIZ_LIST_FETCH_TEACHER_REQUEST);
    }
}
