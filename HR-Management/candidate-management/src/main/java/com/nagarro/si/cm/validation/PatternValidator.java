package com.nagarro.si.cm.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternValidator implements Validator {

    private Pattern pattern;

    public PatternValidator(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public boolean validateData(String data) {
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }
}
