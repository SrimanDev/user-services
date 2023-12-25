package com.validations;



import com.exception.InvalidDataException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    private static final int MAX_PASSWORD_LENGTH = 20;
    public static final String PASSWORD_REGEX="^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,}$";

     private Pattern pattern;

     public PasswordValidator(){

        // Pattern pattern = Pattern.compile(PASSWORD_REGEX);
         this.pattern=Pattern.compile(PASSWORD_REGEX);
     }
    public void checkPassword(String password){
        if( password == null||password.isEmpty()){
            throw new InvalidDataException("Password cannot be null or empty");
        }

        if (password.length() > MAX_PASSWORD_LENGTH) {
            throw new InvalidDataException(String.format("Password is too long: max number of chars is %s",
                    MAX_PASSWORD_LENGTH));
        }

        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new InvalidDataException("Password must to be at least 8 chars, 1 number, 1 upper case," +
                    " 1 lower case letter, 1 special char, no spaces");
        }
    }
}
