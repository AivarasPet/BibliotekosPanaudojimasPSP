package com.PSP.Validators;

import com.PSP.Models.User;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;
import validator.EmailValidator;
import validator.PasswordChecker;
import validator.PhoneValidator;

@Component
public class UserValidator {
    private PhoneValidator phoneValidator;
    private EmailValidator emailValidator;
    private PasswordChecker passwordChecker;
    private String specialPasswordCharacters = "!";
    private int passwordLength = 10;

    public UserValidator() {
        this.emailValidator = new EmailValidator();
        this.passwordChecker = new PasswordChecker();
        this.phoneValidator = new PhoneValidator();
        phoneValidator.addValidation( "+370", 12);
    }

    public boolean validateEmail(User user) {
        return (emailValidator.validate(user.getEmail()));
    }

    public boolean validatePhoneNumber(User user) {
        return phoneValidator.validate(user.getPhoneNumber());
    }

    public boolean validatePassword(User user) {
        return passwordChecker.isPasswordValid(user.getPassword(), passwordLength, specialPasswordCharacters);
    }

}
