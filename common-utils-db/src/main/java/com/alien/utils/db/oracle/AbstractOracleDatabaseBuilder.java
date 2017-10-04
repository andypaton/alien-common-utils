package com.alien.utils.db.oracle;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.alien.utils.db.DropUserTearDownHandler;
import com.alien.utils.db.TearDownHandler;
import com.alien.utils.db.environment.EnvironmentBuilder;

public abstract class AbstractOracleDatabaseBuilder implements EnvironmentBuilder {

    private final String name;
    private final String password;
    private TearDownHandler tearDownHandler;
    private final String username;

    public AbstractOracleDatabaseBuilder(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        
        setTearDownHandler(new DropUserTearDownHandler()); 
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        AbstractOracleDatabaseBuilder rhs = (AbstractOracleDatabaseBuilder) obj;
        return new EqualsBuilder()
            .append(name, rhs.getName())
            .isEquals();
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public TearDownHandler getTearDownHandler() {
        return tearDownHandler;
    }
    
    public String getUsername() {
        return username;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 41)
            .append(name)
            .toHashCode();
    }

    public void setTearDownHandler(TearDownHandler tearDownHandler) {
        this.tearDownHandler = tearDownHandler;
    }

    @Override
    public String toString() {
        return name;
    }

}
