package org.maccarthyjoshua.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.maccarthyjoshua.api.model.User;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@RestController
public class UserService {

    private List<User> userList;

    public UserService() {
        userList = new ArrayList<>();
    }

    public String addUser(String jsonFilePath) {
        try {
            Iterator<JsonNode> iterator = getIterator(jsonFilePath);

            while (iterator.hasNext()) {
                JsonNode jsonNode = iterator.next();

                String userName = jsonNode.get("Username").asText();
                String password = jsonNode.get("Password").asText();
                String email = jsonNode.get("Email").asText();
                String DoB = jsonNode.get("DoB").asText();
                String creditCardNumber = jsonNode.get("Credit Card Number").asText();

                System.out.println(userName);
                System.out.println(password);
                System.out.println(email);
                System.out.println(DoB);
                System.out.println(creditCardNumber);

                User user = new User(userName, password, email, DoB, creditCardNumber);
                userList.add(user);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "user added";
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

    private static Iterator<JsonNode> getIterator(String filepath) throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(filepath)));

        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse JSON string into a JsonNode object
        JsonNode rootNode = objectMapper.readTree(jsonContent);
        // Iterate over the JSON array

        return rootNode.get("accounts").iterator();
    }
}
