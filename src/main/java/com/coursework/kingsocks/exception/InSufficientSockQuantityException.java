package com.coursework.kingsocks.exception;

public class InSufficientSockQuantityException extends RuntimeException{            //Исключение из правила о недостаточном количестве носков


    public InSufficientSockQuantityException(String message) {
        super(message);
    }
}
