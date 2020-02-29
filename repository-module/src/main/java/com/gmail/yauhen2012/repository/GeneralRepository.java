package com.gmail.yauhen2012.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface GeneralRepository<T> {

    T add(Connection connection, T shop) throws SQLException;

    void delete(Connection connection, Integer id) throws SQLException;

}
