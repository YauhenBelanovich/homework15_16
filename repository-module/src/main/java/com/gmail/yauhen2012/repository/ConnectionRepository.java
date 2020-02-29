package com.gmail.yauhen2012.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionRepository {

    Connection getConnection() throws SQLException;

}
