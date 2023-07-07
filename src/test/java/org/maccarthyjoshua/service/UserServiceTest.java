package org.maccarthyjoshua.service;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {
    @Test
    public void userNameHasSpecialCharsOnly() {
        UserService userService = new UserService();
        boolean newUsername = userService.isAlphaNumeric("!@#$%^&*");
        Assert.assertEquals(false, newUsername);
    }

    @Test
    public void usernameIsAlphaNumericOnly() {
        UserService userService = new UserService();
        boolean newUsername = userService.isAlphaNumeric("abc123");
        assertTrue(newUsername);
    }

    @Test
    public void testNoWhiteSpaces() {
        UserService userService = new UserService();
        boolean newUsername = userService.isWhiteSpace("testUsername");
        Assert.assertFalse(newUsername);
    }

    @Test
    public void testWhiteSpaces() {
        UserService userService = new UserService();
        boolean newUsername = userService.isWhiteSpace("t esername");
        Assert.assertTrue(newUsername);
    }

    @Test
    public void useNameIsEightChar() {
        UserService userService = new UserService();
        boolean newUsername = userService.isMinimumLength("eightcha");
        Assert.assertTrue(newUsername);
    }

    @Test
    public void testPassHasNumber() {
        UserService userService = new UserService();
        boolean newUsername = userService.containsNumber("password1");
        Assert.assertTrue(newUsername);
    }

    @Test
    public void passHasNoNumber() {
        UserService userService = new UserService();
        boolean pass = userService.containsNumber("password");
        Assert.assertFalse(pass);
    }

    @Test
    public void passHasUppercase() {
        UserService userService = new UserService();
        boolean pass = userService.containsUpperCase("Password");
        Assert.assertTrue(pass);
    }
    @Test
    public void passNoUppercase() {
        UserService userService = new UserService();
        boolean pass = userService.containsUpperCase("password");
        Assert.assertFalse(pass);
    }

    @Test
    public void emailsValid() {
        UserService userService = new UserService();
        boolean email = userService.isEmailValid("email@mail.com");
        Assert.assertTrue(email);
    }

    @Test
    public void emailIsNotValid() {
        UserService userService = new UserService();
        boolean email = userService.isEmailValid("email.com");
        Assert.assertFalse(email);
    }

    @Test
    public void validDOB() {
        UserService userService = new UserService();
        boolean DoB = userService.isISO8601Date("2023-06-27");
        Assert.assertTrue(DoB);
    }

    @Test
    public void invalidDOB() {
        UserService userService = new UserService();
        boolean DoB = userService.isISO8601Date("06/27/2023");
        Assert.assertFalse(DoB);
    }

    @Test
    public void validCreditCard() {
        UserService userService = new UserService();
        boolean creditCard = userService.isValidCreditCard("1234567890111213");
        Assert.assertTrue(creditCard);
    }

    @Test
    public void invalidCreditCard() {
        UserService userService = new UserService();
        boolean creditCard = userService.isValidCreditCard("12340111213");
        Assert.assertFalse(creditCard);
    }

    @Test
    public void basicDataIsValid() {
        UserService userService = new UserService();
        boolean validRegisterData = userService.isBasicDataValid("user1","Password123", "user1@gmail.com", "2023-06-27", "1234567890121314");
        Assert.assertTrue(validRegisterData);
    }

    @Test
    public void overEighteen() {
        UserService userService = new UserService();
        Assert.assertTrue(userService.isEighteen("2002-06-27"));
    }

    @Test
    public void underEighteen() {
        UserService userService = new UserService();
        Assert.assertFalse(userService.isEighteen("2010-06-27"));
    }

    @Test
    public void testValidCC() {
        UserService userService = new UserService();
        Assert.assertTrue(userService.isValidCreditCard("1234567890111213"));
    }

    @Test
    public void testInvalidCC() {
        UserService userService = new UserService();
        Assert.assertFalse(userService.isValidCreditCard("13"));
    }

    @Test
    public void testLowestValidAmount() {
        //smallest possible value
        UserService userService = new UserService();
        Assert.assertTrue( userService.isValidAmount(100));
    }

    @Test
    public void testHighestValidAmount() {
        //largest possible value
        UserService userService = new UserService();
        Assert.assertTrue(userService.isValidAmount(999));
    }

    @Test
    public void testInvalidUpperBoundary() {
        //4 digit number
        UserService userService = new UserService();
        Assert.assertFalse(userService.isValidAmount(1000));
    }
    @Test
    public void testInvalidLowerBoundary() {
        //4 digit number
        UserService userService = new UserService();
        Assert.assertFalse(userService.isValidAmount(99));
    }

    @Test
    public void testNegativeAmount() {
        //negative Number
        UserService userService = new UserService();
        Assert.assertFalse(userService.isValidAmount(-500));
    }

    @Test
    public void paymentDataValid() {
        //16 digits and typical input
        UserService userService = new UserService();
        Assert.assertTrue(userService.isPaymentDataValid("1234567890123456", 500));
    }

    @Test
    public void paymentDataInvalid() {
        //17 digits and lower than expected amount
        UserService userService = new UserService();
        Assert.assertFalse(userService.isPaymentDataValid("12345678901234561", 10));
    }

}