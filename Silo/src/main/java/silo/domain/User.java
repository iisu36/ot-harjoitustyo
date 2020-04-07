/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silo.domain;

import java.util.Objects;

/**
 *
 * @author Iizu
 */
public class User {
    
    int id;
    String username;
    String password;
    //ArrayList<User>t;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return this.username;

    }

    public int getId() {

        return this.id;
    }

    public String getPassword() {

        return this.password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
