package requestclasses;

import Utilities.ServerRequest;

import java.io.Serializable;

public class StudentLoginRequest implements Serializable {
    private String email;
    private String password;

    public StudentLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        System.out.println("return student login");
        return String.valueOf(ServerRequest.STUDENT_LOGIN_REQUEST);
    }
}

