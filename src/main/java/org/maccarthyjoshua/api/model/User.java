package org.maccarthyjoshua.api.model;

public class User {
    private String username;
    private String password;
    private String email;
    private String DoB;
    private String creditCard;
    private int amount;

    public User(String username, String password, String email, String doB, String creditCard, int amount) {
        this.username = username;
        this.password = password;
        this.email = email;
        DoB = doB;
        this.creditCard = creditCard;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getDoB() {
        return DoB;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public int getAmount() {
        return amount;
    }
}
