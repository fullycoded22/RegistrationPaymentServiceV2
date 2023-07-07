package org.maccarthyjoshua.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.maccarthyjoshua.api.model.User;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


@RestController
public class UserService {
    private final List<User> userList;

    public UserService() {
        userList = new ArrayList<>();
    }

    public void addUser(String jsonFilePath) {
        try {
            Iterator<JsonNode> iterator = getIterator(jsonFilePath);

            while (iterator.hasNext()) {
                JsonNode jsonNode = iterator.next();

                String userName = jsonNode.get("Username").asText();
                String password = jsonNode.get("Password").asText();
                String email = jsonNode.get("Email").asText();
                String DoB = jsonNode.get("DoB").asText();
                String creditCardNumber = jsonNode.get("Credit Card Number").asText();
                int amount = jsonNode.get("Amount").asInt();

                User user = new User(userName, password, email, DoB, creditCardNumber,amount);
                userList.add(user);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<User> getUser(String username) {
        Optional<User> optional = Optional.empty();
        for (User user: userList) {
            if(username == user.getUsername()){
                optional = Optional.of(user);
                return optional;
            }
        }
        return optional;
    }

    private Iterator<JsonNode> getIterator(String filepath) throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(filepath)));

        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse JSON string into a JsonNode object
        JsonNode rootNode = objectMapper.readTree(jsonContent);
        // Iterate over the JSON array

        return rootNode.get("accounts").iterator();
    }

    public boolean isDuplicateUsername(String username) {
        if (userList.isEmpty()) {
            return false;
        }

        for (int i = 1; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }
    public boolean isBasicDataValid(String username, String password, String email, String DoB, String creditCard) {
        return isAlphaNumeric(username)&!
                isWhiteSpace(username)&&
                isMinimumLength(password)&&
                containsNumber(password)&&
                containsUpperCase(password)&&
                isEmailValid(email)&&
                isISO8601Date(DoB)&&
                isValidCreditCard(creditCard);
    }
    public boolean isCreditCardRegistered(String creditCardNumber) {
        for (User user : userList) {
            if (user.getCreditCard().equals(creditCardNumber)){
                return true;
            }
        }
        return false;
    }
    public boolean isPaymentDataValid(String creditCard, int amount) {
        return isValidCreditCard(creditCard) && isValidAmount(amount);
    }

    boolean isAlphaNumeric(String username){

        if(username.matches("^[a-zA-Z0-9]*$")){
            return true;
        }
        return false;
    }

    boolean isWhiteSpace(String username){
        for (int i = 0; i < username.length(); i++) {

            if(Character.isWhitespace(username.charAt(i))){
                return true;
            }
        }
        return false;
    }

    boolean isMinimumLength(String pass){
        int minimumLength = 8;
        if(pass.length()>= minimumLength){
            return true;
        }
        return false;
    }

    boolean containsNumber(String pass){
        for (int i = 0; i < pass.length(); i++) {

            if(Character.isDigit(pass.charAt(i))){
                return true;
            }
        }
        return false;
    }

    boolean containsUpperCase(String pass) {
        for (int i = 0; i < pass.length(); i++) {

            if(Character.isUpperCase(pass.charAt(i))){
                return true;
            }
        }
        return false;
    }

    boolean isEmailValid(String email) {
        return email.contains("@");
    }

    boolean isISO8601Date(String DoB) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

        try {
            LocalDate.parse(DoB, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean isEighteen(String DoB) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(LocalDate.parse(DoB), currentDate);
        return period.getYears() >= 18;
    }

    boolean isValidCreditCard(String num){
        return num.matches("\\d{16}");
    }
    boolean isValidAmount(int amount){
        return amount >= 100 && amount <= 999;
    }

    public List<User>displayUsersWithCreditCard(){
        List<User> creditCardList= new ArrayList<>();
        for (User user : userList) {
            if (!user.getCreditCard().isBlank())
                creditCardList.add(user);
        }
        return creditCardList;
    }

    public List<User>displayUsersWithNoCreditCard(){
        List<User> creditCardList= new ArrayList<>();
        for (User user : userList) {
            if (user.getCreditCard().isBlank())
                creditCardList.add(user);
        }
        return creditCardList;
    }

    public List<User> getUserList() {
        return userList;
    }
}
