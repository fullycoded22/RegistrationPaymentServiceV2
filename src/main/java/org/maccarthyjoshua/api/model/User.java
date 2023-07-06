package org.maccarthyjoshua.api.model;

public class User {
    private final int MIN_PASS_LENGTH = 8;

    private String username;
    private String password;
    private String email;
    private String DoB;
    private String creditCard;

    public User(String username, String password, String email, String doB, String creditCard) {
        this.username = username;
        this.password = password;
        this.email = email;
        DoB = doB;
        this.creditCard = creditCard;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDoB() {
        return DoB;
    }

    public void setDoB(String doB) {
        DoB = doB;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }
}
