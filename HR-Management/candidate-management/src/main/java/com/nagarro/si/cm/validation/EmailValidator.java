package com.nagarro.si.cm.validation;

public class EmailValidator extends PatternValidator {

    public EmailValidator() {
        super("[^@]+@[^@]+\\.[a-zA-Z]{2,3}");
    }
}
