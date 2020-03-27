package org.pesmypetcare.usermanagerlib.datacontainers;


public class UserData {
    private String username;
    private String email;

    /**
     * The method that returns the user's username.
     * @return The user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * The method that set the user's username.
     * @param username The user's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * The method that returns the user's email.
     * @return The user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * The method that set the user's email.
     * @param email The user's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "{"
            + "username='" + username + '\''
            + ", email='" + email + '\''
            + '}';
    }
}
