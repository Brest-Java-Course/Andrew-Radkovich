package com.andrew.service.exception;

/**
 * Created by andrew on 31.12.14.
 */
public class InvalidDateException extends RuntimeException {

    private String objectId;

    public InvalidDateException() {
        super();
    }

    public InvalidDateException(String message, String objectId) {

        super(message);
        this.objectId = objectId;
    }

    public String getObjectId() {

        return objectId;
    }
}
