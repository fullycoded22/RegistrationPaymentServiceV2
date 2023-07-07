package org.maccarthyjoshua.api.controller;

import org.maccarthyjoshua.api.model.User;
import org.maccarthyjoshua.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<String> setUser(@RequestParam String jsonFilePath){
        userService.addUser(jsonFilePath);
        return ResponseEntity.ok("Successfully added JSON data");
    }

    @GetMapping("/user/creditCard")
    public ResponseEntity<List<User>> creditCardFilter(@RequestParam String response){
        List<User> creditCardList= new ArrayList<>();

        if(response.equalsIgnoreCase("yes")){
            return new ResponseEntity<>(userService.displayUsersWithCreditCard(), HttpStatus.OK);
        }else if(response.equalsIgnoreCase("no")){
            return new ResponseEntity<>(userService.displayUsersWithNoCreditCard(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(userService.getUserList(), HttpStatus.OK);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUser(@RequestParam String userName){
        Optional<User> user = userService.getUser(userName);

        if(user.isPresent()){
            if(userService.isDuplicateUsername(userName)){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate Entry. Account Rejected");
            }else if(!userService.isBasicDataValid(user.get().getUsername(),user.get().getPassword(),user.get().getEmail(),user.get().getDoB(),user.get().getCreditCard())){
                return ResponseEntity.badRequest().body("Failed Basic Validation");
            }else if(!userService.isEighteen(user.get().getDoB())){
                return new ResponseEntity<String>("The user is under 18!", HttpStatus.FORBIDDEN);
            }else {
                return ResponseEntity.ok("User Successfully Checked!");
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }
    }

    @PostMapping("/payment")
    public ResponseEntity<String> setPayment(@RequestParam String jsonFilePath){
        userService.addUser(jsonFilePath);
        return ResponseEntity.ok("Successful Payment");
    }

    @GetMapping("/payment")
    public ResponseEntity<String> getPayment(@RequestParam String userName){
        Optional<User> user = userService.getUser(userName);

        if(!userService.isCreditCardRegistered(user.get().getCreditCard())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No User found with Credit Card Details. Payment Rejected");
        }else if(!userService.isPaymentDataValid(user.get().getCreditCard(),user.get().getAmount())){
            return ResponseEntity.badRequest().body("Failed Basic Validation");
        }
        return ResponseEntity.ok("Successful Payment");
    }


}
