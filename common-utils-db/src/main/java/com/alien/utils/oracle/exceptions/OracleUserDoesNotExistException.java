package com.alien.utils.oracle.exceptions;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

public class OracleUserDoesNotExistException extends DataAccessException {

    private static final long serialVersionUID = -7081973494814285703L;

    public OracleUserDoesNotExistException(String msg, SQLException e) {
        super(msg, e);
    }
}
