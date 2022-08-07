package classes;

import java.io.Serializable;
import java.util.List;

public class Subject implements Serializable {
    private List<String> teacherID;//list of Unique ID of Teachers of the subject
    private List<String> examID;
    private List<Student> studentList;//List of students in the classroom
    private String subjectName;//Name of subject
    private String subjectID;

    public Subject(List<String> teacherID) {
        this.teacherID = teacherID;
    }

    public Subject(Subject subject){
        this.teacherID=subject.getTeacherID();
        this.subjectName=subject.getSubjectName();
    }

    public Subject(String subjectID,String subjectName) {
        this.subjectID = subjectID;
        this.setSubjectName(subjectName);
    }

    public Subject(String subjectID) {
        this.subjectID = subjectID;
    }

    public List<String> getExamID() {
        return examID;
    }

    public void setExamID(List<String> examID) {
        this.examID = examID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public List<String> getTeacherID() {
        return teacherID;
    }
    public void setTeacherID(List<String> teacherID) {
        this.teacherID = teacherID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }



    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public String toString() {
        return this.subjectName;
    }
}
