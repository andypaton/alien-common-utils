package com.alien.utils.db;

import java.sql.Connection;

import javax.sql.DataSource;

public interface DatabaseDriver {

    void cleanUp();
    Connection createConnectionFor(String username, String password);
    DataSource createDataSourceFor(String username, String password);
    void createUser(String username, String password);
    void dropUser(String username);
    void dropUserQuietly(String username);
    boolean hasTable(String esername, String tableName);
    void truncateTable(String username, String tableName);
    boolean userExists(String username);
}
