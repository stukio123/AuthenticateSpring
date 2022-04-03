package com.example.userservice.Utils;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements Predicate<String> {
    private final String regex = "^(.+)@(.+)%";
    private final Pattern pattern = Pattern.compile(regex);

    @Override
    public boolean test(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
