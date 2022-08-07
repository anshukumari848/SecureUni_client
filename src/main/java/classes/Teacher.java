package classes;

import java.io.Serializable;

public class Teacher extends User implements Serializable {
    private String teacherID;//unique ID for a teacher
    private String teacherLoginStatus;

    public Teacher(String userID,String teacherID) {
        this.setUserID(userID);
        this.teacherID = teacherID;
    }
    public String getTeacherID() {
        return teacherID;
    }


    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacherLoginStatus() {
        return teacherLoginStatus;
    }

    public void setTeacherLoginStatus(String teacherLoginStatus) {
        this.teacherLoginStatus = teacherLoginStatus;
    }


}
