package classes;

import java.io.Serializable;

public class User implements Serializable {
    private String userid;
    private String fName;
    private String lName;
    private String email;
    private String job;

    public String getUserID(){
        return userid;
    }
    public void setUserID(String userid)
    {
        this.userid = userid;
    }
    public String getFirstName(){
        return fName;
    }
    public void setFirstName(String fName)
    {
        this.fName = fName;
    }
    public String getLastName(){
        return lName;
    }
    public void setLastName(String lName)
    {
        this.lName = lName;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getJob(){
        return job;
    }
    public void setJob(String job)
    {
        this.job = job;
    }
}
