package com.nagarro.si.cm.validation;

public class PhoneNumberValidator extends PatternValidator {

    public PhoneNumberValidator() {
        super("^([+]|0{1})\\d{1,14}");
    }
}
