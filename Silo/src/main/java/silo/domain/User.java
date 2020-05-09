package silo.domain;

import java.util.Objects;

/**
 * @author Iizu
 * @version Viikko 7
 *
 * This class controls the user's values.
 */
public class User {

    String username;
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return this.username;

    }

    public String getPassword() {

        return this.password;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        User user = (User) object;
        return username.equals(user.username)
                && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
