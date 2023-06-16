package common.interaction;

import java.io.Serializable;

/**
 * Class for get username and password.
 */
public class User implements Serializable {
    private String username;
    private byte[] password;

    public User(String username, byte[] password) {
        this.username = username;
        this.password = password;
    }

    /**
     * @return Username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return Password.
     */
    public byte[] getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return username + ":" + password;
    }

    @Override
    public int hashCode() {
        return username.hashCode() + password.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof User) {
            User userObj = (User) obj;
            return username.equals(userObj.getUsername()) && password.equals(userObj.getPassword());
        }
        return false;
    }
}