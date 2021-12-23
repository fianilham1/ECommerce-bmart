package com.tes.buana.common.util;

import java.util.regex.Pattern;

public class RegexUtil {

    public static boolean isValidEmail(String email) {
        String emailRgx = "^([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})";
        return Pattern.matches(emailRgx, email);
    }

    public static boolean isValidPassword(String password){
        String passwordRgx = "^[a-zA-Z0-9@\\\\#$%&*()_+\\]\\[';:?.,!^-]{8,}$";
        return Pattern.matches(passwordRgx, password);
    }
}
