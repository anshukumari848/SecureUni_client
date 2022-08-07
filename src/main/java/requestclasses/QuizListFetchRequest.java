package requestclasses;

import Utilities.ServerRequest;

import java.io.Serializable;

public class QuizListFetchRequest implements Serializable {
    private String SubjectID;
    private String teacherID;
    private String studentID;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public QuizListFetchRequest(String subjectID) {
        SubjectID = subjectID;
        this.teacherID = "";
    }
    public QuizListFetchRequest(){
        System.out.println("hi");
    }

    public String getTeacherID() {
        return teacherID;
    }
    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(String subjectID) {
        SubjectID = subjectID;
    }

    @Override
    public String toString() {
        System.out.println("hi");
        return String.valueOf(ServerRequest.EXAM_LIST_FETCH_REQUEST);
    }
}
