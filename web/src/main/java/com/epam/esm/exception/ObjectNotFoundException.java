package com.epam.esm.exception;

public class ObjectNotFoundException extends RuntimeException {
    private final long objectID;

    public ObjectNotFoundException(String message, long objectID) {
        super(message);
        this.objectID = objectID;
    }

    public long getObjectID() {
        return objectID;
    }
}
