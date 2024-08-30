package com.Beevago.App.exceptions;

public class PasswordConfirmedNotEqualsUserPassword extends Exception {
    
    public PasswordConfirmedNotEqualsUserPassword(String message) {
        super(message);
    }

    private static final long serialVersionUID = 1L;

}
