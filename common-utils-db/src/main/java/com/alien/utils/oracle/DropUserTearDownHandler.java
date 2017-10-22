package com.alien.utils.oracle;

public class DropUserTearDownHandler implements TearDownHandler {

    @Override
    public void tearDown(DatabaseDriver databaseDriver, String username) {
        databaseDriver.dropUser(username);
    }
}
