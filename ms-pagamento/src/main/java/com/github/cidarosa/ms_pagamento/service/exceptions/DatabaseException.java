package com.github.cidarosa.ms_pagamento.service.exceptions;

public class DatabaseException extends RuntimeException{

    public DatabaseException(String message) {
        super(message);
    }
}
