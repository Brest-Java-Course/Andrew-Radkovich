package com.epam.brest.courses.rest.controller.exception;

/**
 * Created by andrew 3.11.14
 */
public class NotFoundException extends RuntimeException{

    private String objectId;

    public NotFoundException(String message, String objectId) {

        super(message);
        this.objectId = objectId;
    }

    public String getObjectId() {

        return objectId;
    }
}
