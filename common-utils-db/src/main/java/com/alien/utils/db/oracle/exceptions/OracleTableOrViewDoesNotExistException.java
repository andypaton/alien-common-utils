package com.alien.utils.db.oracle.exceptions;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

public class OracleTableOrViewDoesNotExistException extends DataAccessException {

    private static final long serialVersionUID = -7081973494814285703L;

    public OracleTableOrViewDoesNotExistException(String msg, SQLException e) {
        super(msg, e);
    }
}
