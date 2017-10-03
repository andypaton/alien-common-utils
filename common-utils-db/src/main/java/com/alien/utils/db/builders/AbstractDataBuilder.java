package com.alien.utils.db.builders;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.sql.DataSource;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.JdbcTemplate;


public class AbstractDataBuilder <B> implements Builder<B>{
    
    public static final Boolean DEFAULT_BOOLEAN = false;
    public static final DateTime DEFAULT_DATE = DateTime.now();
    public static final Integer DEFAULT_INTEGER = 1;
    public static final String DEFAULT_STRING = "DEFAULT_VALUE";

    public static final String DEFAULT_LOCKTOKENTEXT = "1";
    public static final String DEFAULT_USER = "LWS_TEST";
    public static final byte[] DEFAULT_BYTE = new byte[1];
    public static final String DEFAULT_XML = "<xml></xml>";

    private static final String INSERT_STATEMENT = "INSERT INTO %s.%s (%s) VALUES (%s)";

    private Map<String, Object> fields = new HashMap<String, Object>();

    private final String schema;
    private final String table;

    protected AbstractDataBuilder(String schema, String table) {

        this.schema = schema;
        this.table = table;
    }

    @SuppressWarnings("unchecked")
    public B createUsing(DataSource dataSource) {

        String sql = format(INSERT_STATEMENT, schema, table, getColumns(), getValues());

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, getArgs());

        return (B) this;
    }

    public static int generateRandomInt() {

        Random random = new Random();
        return random.nextInt();
    }

    public static String generateRandomString(int length) {

        return RandomStringUtils.randomAlphanumeric(length);
    }
    
    public static String generateRandomStringAlphabetic(int length) {

        return RandomStringUtils.randomAlphabetic(length);
    }


    public <T> T getField(String name, Class<T> type) {

        return type.cast(fields.get(name));
    }

    public boolean hasField(String name) {

        return fields.containsKey(name);
    }

    public void setField(String name, Object value) {

        fields.put(name, value);
    }

    private Object[] getArgs() {

        List<Object> args = new ArrayList<Object>();

        for (String key : fields.keySet()) {
            Object obj = fields.get(key);

            if (obj instanceof DateTime) {
                args.add(((DateTime) obj).toDate());
            } else {
                args.add(obj);
            }
        }

        return args.toArray();
    }

    private String getColumns() {

        boolean first = true;
        StringBuilder sb = new StringBuilder();

        for (String key : fields.keySet()) {
            if (!first) {
                sb.append(",");
            }

            sb.append(key);
            sb.append(" ");

            first = false;
        }

        return sb.toString();
    }

    private String getValues() {

        boolean first = true;
        StringBuilder sb = new StringBuilder();

        for (int x = 0; x < fields.keySet().size(); x++) {

            if (!first) {
                sb.append(",");
            }

            sb.append("?");

            first = false;
        }

        return sb.toString();
    }

}
