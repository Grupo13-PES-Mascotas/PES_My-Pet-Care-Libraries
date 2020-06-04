package org.pesmypetcare.usermanager.datacontainers.user;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * @author Santiago Del Rey
 */
public class UserDataSender {
    private String uid;
    private String username;
    private String password;
    private String email;

    /**
     * Default constructor.
     */
    public UserDataSender() {
    }

    /**
     * Creates a UserData object with the specified username, email and password.
     *
     * @param uid The user's UID
     * @param username The user's username
     * @param email The user's email
     * @param password The user's password
     */
    public UserDataSender(String uid, String username, String email, String password) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        return "UserDataSender{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", email='"
               + email + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDataSender that = (UserDataSender) o;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getPassword(), that.getPassword())
               && Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getEmail());
    }
}
