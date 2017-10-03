package com.alien.utils.db.oracle.exceptions;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

public class OracleUserAlreadyExistsException extends DataAccessException {

    private static final long serialVersionUID = -7081973494814285703L;

    public OracleUserAlreadyExistsException(String msg, SQLException e) {
        super(msg, e);
    }
}
