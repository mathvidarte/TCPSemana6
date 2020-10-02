package com.example.tcpsemana6.model;

public class Usuario {
    private String user;
    private String password;
    private String id;


    public Usuario(String id, String user, String password) {
        this.id = id;
        this.user = user;
        this.password = password;
    }
    public Usuario () {

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
