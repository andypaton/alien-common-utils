package com.alien.utils.oracle;

import static java.lang.String.format;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.alien.utils.oracle.environment.EnvironmentBuildFailureException;
import com.alien.utils.oracle.exceptions.OracleSQLExceptionTranslator;
import com.alien.utils.oracle.exceptions.OracleTableOrViewDoesNotExistException;
import com.alien.utils.oracle.exceptions.OracleUserAlreadyExistsException;
import com.alien.utils.oracle.exceptions.OracleUserDoesNotExistException;


public class OracleDatabaseDriver implements DatabaseDriver {

    public static final String DEFAULT_PORT = "1521";
    public static final String DEFAULT_SID = "MCS11G";
    public static final String SYSTEM_PASSWORD = "Oracle123";
    public static final String SYSTEM_USERNAME = "system";

    private static final Logger LOG = LoggerFactory.getLogger(OracleDatabaseDriver.class);

    private final Set<Connection> connectionsCreated = new HashSet<Connection>();
    private final DataSource dataSource;
    private final String hostname;
    private final JdbcTemplate jdbcTemplate;

    public OracleDatabaseDriver(String hostname) {
        this.hostname = hostname;

        try {
            dataSource = createDataSource(SYSTEM_USERNAME, SYSTEM_PASSWORD);

            jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.setExceptionTranslator(new OracleSQLExceptionTranslator());

        } catch (SQLException e) {
            throw new EnvironmentBuildFailureException(
                format("Unable to create database connection as %s/%s", SYSTEM_USERNAME, SYSTEM_PASSWORD), e);
        }
    }

    @Override
    public void cleanUp() {
        cleanUpUserConnections();
    }

    @Override
    public Connection createConnectionFor(String username, String password) {

        Connection con;
        try {
            con = createConnection(username, password);
        } catch (EnvironmentBuildFailureException e) {
            try {
                createUser(username, password);
            } catch (OracleUserAlreadyExistsException e2) {
                dropUser(username);
                createUser(username, password);
            }
            con = createConnection(username, password);
        }

        if (con != null) {
            connectionsCreated.add(con);
            LOG.debug("Created connection for {}", username);
        }

        return con;
    }

    @Override
    public DataSource createDataSourceFor(String username, String password) {
        try {
            return createDataSource(username, password);
        } catch (SQLException e) {
            LOG.error("Unable to create DataSource", e);
            return null;
        }
    }

    @Override
    public void createUser(String username, String password) {

        executeQuery("CREATE USER %s IDENTIFIED BY %s DEFAULT TABLESPACE users TEMPORARY TABLESPACE temp"
                , username, password);
        executeQuery("GRANT DBA TO %s", username);
        executeQuery("GRANT ALL PRIVILEGES TO %s", username);

        LOG.info("Created database user '{}'", username);
    }

    @Override
    public void dropUser(String username) {
        try {
            cleanUpUserConnections();
            killDatabaseSessionsQuietly(username);
            LOG.info("Dropping database user '{}'", username);
            jdbcTemplate.execute(format("DROP USER %s CASCADE", username));
        } catch (OracleUserDoesNotExistException e) {
            // Ignore exception
            LOG.warn("Failed to drop user, ignoring exception '{}'", e.getMessage());
        }
    }

    /** 
     * @deprecated Use the dropUser(username) method as it does exactly the same thing
     */
    @Override
    @Deprecated
    public void dropUserQuietly(String username) {
        dropUser(username);
    }

    @Override
    public boolean hasTable(String username, String tableName) {
        boolean tableExists = false;
        try {
            jdbcTemplate.execute(format("SELECT * FROM %s.%s", username, tableName));
            tableExists = true;
        } catch (OracleTableOrViewDoesNotExistException e) {
            tableExists = false;
        }
        return tableExists;
    }

    @Override
    public void truncateTable(String username, String tableName) {
        LOG.info("Truncating database table '{}'", tableName);
        jdbcTemplate.execute(format("TRUNCATE TABLE %s.%s", username, tableName));
    }

    @Override
    public boolean userExists(String username) {
        int count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM ALL_USERS WHERE USERNAME=UPPER(?)", new Object[] { username });

        return count == 1 ? true : false;
    }

    private void cleanUpUserConnections() {
        for (Connection con : connectionsCreated) {
            closeQuietly(con);
        }
    }

    private void closeQuietly(Connection con) {
        try {
            if (con != null) {
                con.close();
                LOG.debug("Closed connection");
            }
        } catch (SQLException e) {
            // Ignore
            LOG.info("Failed to close connection quietly", e);
        }
    }

    private Connection createConnection(String username, String password) {
        try {
            DataSource ds = createDataSource(username, password);
            return ds.getConnection();
        } catch (SQLException e) {
            throw new EnvironmentBuildFailureException(
                    format("Unable to create JDBC connection for %s/%s", username, password), e);
        }
    }

    private DataSource createDataSource(String username, String password) throws SQLException {
        OracleDataSource ds = new OracleDataSource();
        ds.setURL(createJdbcUrl(hostname));
        ds.setUser(username);
        ds.setPassword(password);
        return ds;
    }

    private String createJdbcUrl(String hostname) {
        return format("jdbc:oracle:thin:@%s:%s:%s", hostname, DEFAULT_PORT, DEFAULT_SID);
    }

    private void executeQuery(String sql, Object... params) {
        jdbcTemplate.execute(format(sql, params));
    }

    private void killDatabaseSessions(String username) {
        Object[] args = {username};
        
        jdbcTemplate.query("SELECT sid, serial# FROM v$session WHERE username = UPPER(?)", args, new RowCallbackHandler() {
            
            @Override
            public void processRow(ResultSet rs) {
                try {
                    String sid      = rs.getString(1);
                    String serial   = rs.getString(2);
                    
                    LOG.info("Killing database session sid - {}, serial - {}", sid, serial);
                
                    jdbcTemplate.update(format("ALTER SYSTEM KILL SESSION '%s,%s' ", sid, serial));
                } catch (Exception e) {
                    // Ignore - can get a nested exception of java.sql.SQLException: ORA-00030: User session ID does not exist.
                    // but that is what we want
                    LOG.debug(e.getMessage(), e);
                }
            }
        });
    }
    
    private void killDatabaseSessionsQuietly(String username) {
        try {
            killDatabaseSessions(username);
        } catch (UncategorizedSQLException e) {
            // Ignore - can get a nested exception of java.sql.SQLException: ORA-00030: User session ID does not exist.
            // but that is what we want
            LOG.debug(e.getMessage(), e);
        }
    }
}
