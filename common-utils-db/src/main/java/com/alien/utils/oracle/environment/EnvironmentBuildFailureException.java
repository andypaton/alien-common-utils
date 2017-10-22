package com.alien.utils.oracle.environment;


public class EnvironmentBuildFailureException extends RuntimeException {

    private static final long serialVersionUID = -1736308251539692259L;

    public EnvironmentBuildFailureException(String message) {
        super(message);
    }

    public EnvironmentBuildFailureException(String message, Exception e) {
        super(message, e);
    }
}
