package com.alien.utils.db;

public class DropUserTearDownHandler implements TearDownHandler {

    @Override
    public void tearDown(DatabaseDriver databaseDriver, String username) {
        databaseDriver.dropUser(username);
    }
}
