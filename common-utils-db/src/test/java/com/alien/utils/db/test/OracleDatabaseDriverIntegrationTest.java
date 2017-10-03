package com.alien.utils.db.test;

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.alien.utils.db.DatabaseDriver;
import com.alien.utils.db.oracle.exceptions.OracleUserAlreadyExistsException;



public class OracleDatabaseDriverIntegrationTest {

    private static final String TEST_USERNAME           = "test_user";
    private static final String TEST_PASSWORD           = "test_password";
    private static final String TEST_TABLE              = "test_table";
    private static final String TABLE_DOES_NOT_EXIST    = "table_does_not_exist";
    private static final String USER_DOES_NOT_EXIST     = "user_does_not_exist";

    private DatabaseDriver driver;

    @Before
    public void setUp() {
        driver = new OracleDatabaseDriver(TEST_HOSTNAME);
        driver.dropUser(TEST_USERNAME);
    }

    @After
    public void tearDown() throws Exception {
        driver.cleanUp();
    }

    @Test
    public void shouldReturnFalseWhenUserDoesNotExist() { // NOPMD by dev on 28/10/13 08:26
        checkUserDoesNotExist(USER_DOES_NOT_EXIST);
    }

    @Test
    public void shouldDropUserWithoutErrorWhenUserDoesNotExist() { // NOPMD by dev on 28/10/13 08:25
        driver.dropUser(USER_DOES_NOT_EXIST);
        checkUserDoesNotExist(USER_DOES_NOT_EXIST);
    }

    @Test
    public void shouldDropUserWhenUserExists() { // NOPMD by dev on 28/10/13 08:25
        createUser();

        driver.dropUser(TEST_USERNAME);
        checkUserDoesNotExist(TEST_USERNAME);
    }

    @Test
    @Ignore("Short term ignore - can't recreate locally, but preventing Jenkins from building.  Ticket on board.  PG 18/11/2014")
    public void shouldDropUserWhenExistingDatabaseConnectionExists() throws Exception { // NOPMD by dev on 28/10/13 
        createUser();
        createUnmanagedConnection();
        
        driver.dropUser(TEST_USERNAME);
        checkUserDoesNotExist(TEST_USERNAME);
    }

    @Test
    public void shouldCreateUserWhenUserDoesNotExist() { // NOPMD by dev on 28/10/13 08:26
        driver.createUser(TEST_USERNAME, TEST_PASSWORD);
        checkUserExists(TEST_USERNAME);
    }

    @Test
    public void shouldTruncateTableWhenTableExists() throws Exception {
        createTable(TEST_TABLE);
        populateTable(TEST_TABLE);

        driver.truncateTable(TEST_USERNAME, TEST_TABLE);
        checkTableIsEmpty(TEST_TABLE);
    }

    @Test(expected = OracleUserAlreadyExistsException.class)
    public void shouldThrowExceptionWhenTryingToCreateUserWhoAlreadyExists() {

        createUser();

        driver.createUser(TEST_USERNAME, TEST_PASSWORD);
    }

    @Test
    public void shouldCreateConnectionWhenUserDoesNotExist() throws Exception {

        Connection con = driver.createConnectionFor(TEST_USERNAME, TEST_PASSWORD);

        assertNotNull("Database connection not created", con);
    }

    @Test
    public void shouldCreateConnectionWhenUserDoesExist() {

        createUser();

        Connection con = driver.createConnectionFor(TEST_USERNAME, TEST_PASSWORD);

        assertNotNull("Database connection not created", con);
    }

    @Test
    public void shouldCreateConnectionWhenUserDoesExistsWithDifferentPassword() {

        String differentPassword = "different_password";

        createUser();

        Connection con = driver.createConnectionFor(TEST_USERNAME, differentPassword);

        assertNotNull("Database connection not created", con);
    }

    @Test
    public void shouldReturnFalseIfTableDoesNotExist() {
        checkTableDoesNotExist(TABLE_DOES_NOT_EXIST);
    }

    @Test
    public void shouldReturnTrueIfTableExists() throws Exception {
        createTable(TEST_TABLE);
        checkTableExists(TEST_TABLE);
    }

    private void checkTableDoesNotExist(String tableName) {
        assertThat("Table exists", driver.hasTable(TEST_USERNAME, tableName), is(false));
    }

    private void checkTableExists(String tableName) {
        assertThat("Table exists", driver.hasTable(TEST_USERNAME, tableName), is(true));
    }

    private void checkUserDoesNotExist(String username) {
        assertThat("Database users exists", driver.userExists(username), is(false));
    }

    private void checkUserExists(String username) {
        assertThat("Database users exists", driver.userExists(username), is(true));
    }

    private void checkTableIsEmpty(String tableName) throws Exception {
        Connection con = driver.createConnectionFor(TEST_USERNAME, TEST_PASSWORD);

        try {
            String sql = format("select count(*) from %s", tableName);
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            rs.next();
            int rowCount = rs.getInt(1);
            assertThat("Row count", rowCount, is(0));
        } finally {
            con.close();
        }
    }

    private void createTable(String tableName) throws Exception {
        Connection con = driver.createConnectionFor(TEST_USERNAME, TEST_PASSWORD);

        try {
            String sql = format("create table %s (id int)", tableName);
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.execute();
        } finally {
            checkTableExists(tableName);
            con.close();
        }
    }

    private void createUser() {
        driver.createUser(TEST_USERNAME, TEST_PASSWORD);
        checkUserExists(TEST_USERNAME);
    }

    private void createUnmanagedConnection() throws SQLException {
        DataSource ds = driver.createDataSourceFor(TEST_USERNAME, TEST_PASSWORD);
        Connection con = ds.getConnection();
    }

    private void populateTable(String tableName) throws Exception {
        Connection con = driver.createConnectionFor(TEST_USERNAME, TEST_PASSWORD);

        try {
            for (int i = 0; i < 10; i++) {
                String sql = format("INSERT INTO %s (id) VALUES (%s)", tableName, i);
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.execute();
            }
        } finally {
            assertThat("Database table exists", driver.hasTable(TEST_USERNAME, TEST_TABLE), is(true));
            con.close();
        }
    }
}
