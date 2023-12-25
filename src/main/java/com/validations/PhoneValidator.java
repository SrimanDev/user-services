package com.validations;

import com.exception.InvalidDataException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator {

    private static final int MAX_PHONE_NUMBER_LENGTH=20;
    private static final String PHONE_REGEX = "^[+]{1}(?:[0-9\\-\\(\\)\\/\\.]\\s?){6,15}[0-9]{1}$";


    private Pattern pattern;

    public PhoneValidator(){

        //Pattern pattern =
        this.pattern=Pattern.compile(PHONE_REGEX);
    }

    public void checkPhoneNumber(String phoneNumber){
        if(phoneNumber==null||phoneNumber.isEmpty()){
            throw new InvalidDataException("phone number cannot be null or empty");
        }
        if (phoneNumber.length() > MAX_PHONE_NUMBER_LENGTH) {
            throw new InvalidDataException(String.format("The phone is too long: max number of chars is %s",
                    MAX_PHONE_NUMBER_LENGTH));
        }

        Matcher matcher = pattern.matcher(phoneNumber);
        if(!matcher.matches()){
            throw new InvalidDataException("phone number is not valid, max length is 10");
        }
    }
}
