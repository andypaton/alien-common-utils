package com.alien.utils.db.builders;

import javax.sql.DataSource;

public interface Builder<T> {

    T createUsing(DataSource dataSource);
}
