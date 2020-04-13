package org.pesmypetcare.usermanagerlib.datacontainers;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserData {
    private String username;
    private String email;
    private String password;

    /**
     * Creates a UserData object with the specified username, email and password.
     * @param username The user's username
     * @param email The user's email
     * @param password The user's password
     */
    public UserData(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Gets the user's username.
     * @return The user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     * @param username The user's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the user's email.
     * @return The user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     * @param email The user's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's password.
     * @return The user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     * @param password The user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        return "{"
            + "username='" + username + '\''
            + ", email='" + email + '\''
            + ", password='" + password + '\''
            + '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof UserData) {
            return ((UserData) obj).getUsername().equals(this.username)
                && ((UserData) obj).getEmail().equals(this.email)
                && ((UserData) obj).getPassword().equals(this.password);
        }
        return false;
    }
}
