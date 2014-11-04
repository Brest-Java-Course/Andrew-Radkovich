package com.epam.brest.courses.rest.exeption;

/**
 * Created by andrew on 4.11.14.
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
