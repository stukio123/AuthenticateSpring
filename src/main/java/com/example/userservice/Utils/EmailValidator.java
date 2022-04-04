package com.example.userservice.Utils;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements Predicate<String> {
    private static final String regex = "^(.+)@(.+)%";
    private static final Pattern pattern = Pattern.compile(regex);

    @Override
    public boolean test(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isEmail(String email){
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
