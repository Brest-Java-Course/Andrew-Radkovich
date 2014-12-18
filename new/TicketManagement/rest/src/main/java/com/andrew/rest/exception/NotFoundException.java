package com.andrew.rest.exception;

/**
 * Created by andrew on 18.12.14.
 */
public class NotFoundException extends RuntimeException {

    private String objectId;

    public NotFoundException(String message, String objectId) {

        super(message);
        this.objectId = objectId;
    }

    public String getObjectId() {

        return objectId;
    }
}
