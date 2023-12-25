package com.validations;

import com.exception.InvalidDataException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final int MAX_LENGTH=75;

    private static final String EMAIL_REGEX="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    private Pattern pattern;
    
    public EmailValidator(){
        Pattern pattern1 = Pattern.compile(EMAIL_REGEX);
    }

    public void checkEmailIsValidOrNot(String email){
        if(email==null|| email.isEmpty()){
            throw new InvalidDataException("Email cannot be null or empty");
        }
        if(email.length()>MAX_LENGTH){
            throw new InvalidDataException(String.format("The length of email should not cross %s",MAX_LENGTH));
        }

        if(!Pattern.matches(EMAIL_REGEX,email)){
            throw new InvalidDataException("The email format is not valid");
        }
    }
}
