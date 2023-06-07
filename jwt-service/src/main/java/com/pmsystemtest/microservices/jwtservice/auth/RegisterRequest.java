package com.pmsystemtest.microservices.jwtservice.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    public List<String> getValidationErrors(){
        List<String> errors = new ArrayList<>();

        // firstname and lastname

        if(firstname == null || firstname.isEmpty()){
            errors.add("firstname can not be empty");
        }

        if(lastname == null || lastname.isEmpty()){
            errors.add("lastname can not be empty");
        }

        // email check

        if(email == null || email.isEmpty()){
            errors.add("email can not be empty");
        }else{
            // check email format
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            Pattern emailPattern = Pattern.compile(emailRegex);
            if(!emailPattern.matcher(email).matches()){
                errors.add("invalid email format");
            }
        }

        // password check

        if(password == null || password.isEmpty()){
            errors.add("password can not be empty");
        }else{

            // check password format
            String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
            Pattern passwordPattern = Pattern.compile(passwordRegex);
            if(!passwordPattern.matcher(password).matches()){
                errors.add("Password must contain at least one uppercase letter, one lowercase letter, and one digit.");
            }
        }
        return errors;
    }
}
