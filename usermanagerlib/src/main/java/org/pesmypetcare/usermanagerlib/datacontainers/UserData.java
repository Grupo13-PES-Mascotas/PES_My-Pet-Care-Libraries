package org.pesmypetcare.usermanagerlib.datacontainers;


public class UserData {
    private String username;
    private String email;

    public UserData() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "{" +
            "username='" + username + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
