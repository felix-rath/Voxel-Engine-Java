package de.rathfelix.exceptions;

public class ShaderException extends Exception{
    public ShaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShaderException(String message) {
        super(message);
    }
}
