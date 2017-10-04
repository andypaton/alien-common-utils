package com.alien.utils.db.generator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;

//import oracle.jdbc.pool.OracleDataSource;
//
//import liquibase.database.Database;
//import liquibase.database.DatabaseFactory;
//import liquibase.database.jvm.JdbcConnection;
//import liquibase.exception.DatabaseException;
//import liquibase.integration.commandline.CommandLineUtils;


public class GenerateChangeLogs_old {

//    public void execute() throws Exception {
//        
//        ChangeLogContext csc_refdata_context = new ChangeLogContext(); 
//        csc_refdata_context.setFileName("csc.refdata.db.xml");
//        csc_refdata_context.setSchema("csc_refdata");
//        csc_refdata_context.setUsername("csc");
//        csc_refdata_context.setPassword("csc");
//        csc_refdata_context.setHostname("ccapedbe05");
//        csc_refdata_context.setPort("1525");
//        csc_refdata_context.setSid("CSC051E");
//        
//        ChangeLogContext csc_context = new ChangeLogContext(); 
//        csc_context.setFileName("csc.db.xml");
//        csc_context.setSchema(null);
//        csc_context.setUsername("csc");
//        csc_context.setPassword("csc");
//        csc_context.setHostname("ccapedbe05");
//        csc_context.setPort("1525");
//        csc_context.setSid("CSC051E");
//
//        ChangeLogContext csc_cbs_services_context = new ChangeLogContext(); 
//        csc_cbs_services_context.setFileName("csc.cbsservices.db.xml");
//        csc_cbs_services_context.setSchema(null);
//        csc_cbs_services_context.setUsername("cbsservices_user");
//        csc_cbs_services_context.setPassword("cbsservices_u5er");
//        csc_cbs_services_context.setHostname("ccapedbe05");
//        csc_cbs_services_context.setPort("1525");
//        csc_cbs_services_context.setSid("CSC051E");
//
//        ChangeLogContext csc_user_context = new ChangeLogContext(); 
//        csc_user_context.setFileName("csc.user.db.xml");
//        csc_user_context.setSchema("csc_user");
//        csc_user_context.setUsername("csc");
//        csc_user_context.setPassword("csc");
//        csc_user_context.setHostname("ccapedbe05");
//        csc_user_context.setPort("1525");
//        csc_user_context.setSid("CSC051E");
//
//        String diffTypes = null;
//        String changeSetAuthor = "lws";
//        String changeSetContext = null;
//        String dataOutputDirectory = "target";
//        
//        generateChangeLog(diffTypes, changeSetAuthor, changeSetContext, dataOutputDirectory, csc_context);        
//        generateChangeLog(diffTypes, changeSetAuthor, changeSetContext, dataOutputDirectory, csc_refdata_context);        
//        generateChangeLog(diffTypes, changeSetAuthor, changeSetContext, dataOutputDirectory, csc_cbs_services_context);        
//        generateChangeLog(diffTypes, changeSetAuthor, changeSetContext, dataOutputDirectory, csc_user_context);        
//    }
//
//    private void generateChangeLog(String diffTypes, String changeSetAuthor, String changeSetContext, String dataOutputDirectory, ChangeLogContext context)
//            throws SQLException, DatabaseException, IOException, ParserConfigurationException {
//        
//        Connection con = createDataSource(context).getConnection();
//        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(con));
//        
//        CommandLineUtils.doGenerateChangeLog(context.getFilename(), database, context.getSchema(), diffTypes , changeSetAuthor , changeSetContext , dataOutputDirectory);
//    }
//    
//    public static void main(String[] args) throws Exception {
//        GenerateChangeLogs changeLogs = new GenerateChangeLogs();
//        changeLogs.execute();
//    }
//
//    private DataSource createDataSource(ChangeLogContext context) throws SQLException {
//        OracleDataSource ds = new OracleDataSource();
//        ds.setURL(createJdbcUrl(context.getHostname(), context.getPort(), context.getSid()));
//        ds.setUser(context.getUsername());
//        ds.setPassword(context.getPassword());
//        return ds;
//    }
//
//    private String createJdbcUrl(String hostname, String port, String sid) {
//        return String.format("jdbc:oracle:thin:@%s:%s:%s", hostname, port, sid);
//    }
}
