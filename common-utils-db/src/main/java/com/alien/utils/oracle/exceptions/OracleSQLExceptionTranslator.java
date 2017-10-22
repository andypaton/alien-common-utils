package com.alien.utils.oracle.exceptions;


import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.support.SQLExceptionTranslator;

/**
 * Implementation of SQLExceptionTranslator that uses Oracle vendor code,
 * as included in the ORA-nnn methods. More precise than SQLState implementation,
 * but Oracle-specific. The JdbcTemplate class enables error handling to be
 * parameterized without making application's dependent on a particular RDBMS.
 */
public class OracleSQLExceptionTranslator implements SQLExceptionTranslator {

    public DataAccessException translate(String task, String sql, SQLException sqlex) {
        switch (sqlex.getErrorCode()) {
            case 1 :
                // Unique constraint violated
                return new DataIntegrityViolationException(task + ": " + sqlex.getMessage(), sqlex);

            case 942 :
                return new OracleTableOrViewDoesNotExistException("Table or view does not exist", sqlex);

            case 1918:
                return new OracleUserDoesNotExistException("User does not exist", sqlex);
            
            case 1920:
                return new OracleUserAlreadyExistsException("User already exists", sqlex);
            
            case 1400:
                //  Can't insert null into non-nullable column
                return new DataIntegrityViolationException(task + ": " + sqlex.getMessage(), sqlex);
            case 936 : 
                    // missing expression
                    return new BadSqlGrammarException(task, sql, sqlex);
        }

        // We couldn't identify it more precisely
        return new UncategorizedSQLException("(" + task + "): encountered SQLException [" + sqlex.getMessage() + "]", sql, sqlex);
    }

}