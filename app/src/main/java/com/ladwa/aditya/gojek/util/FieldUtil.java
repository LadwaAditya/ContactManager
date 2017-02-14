package com.ladwa.aditya.gojek.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Created by Aditya on 12-Feb-17.
 */

public class FieldUtil {

    //Prevent Accidental Object creation
    private FieldUtil() {
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
