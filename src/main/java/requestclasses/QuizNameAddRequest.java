package requestclasses;

import Utilities.ServerRequest;
import classes.Subject;

import java.io.Serializable;

public class QuizNameAddRequest implements Serializable {
    private String examID;
    private String teacherID;
    private String subjectID;
    private Subject subject;
    private String examName;


    public QuizNameAddRequest(String examID, String teacherID, String subjectID) {
    this.examID = examID;
        this.teacherID = teacherID;
        this.subjectID = subjectID;
}

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }



    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    @Override
    public String toString() {
        return String.valueOf(ServerRequest.EXAM_ADD_REQUEST);
    }
}
