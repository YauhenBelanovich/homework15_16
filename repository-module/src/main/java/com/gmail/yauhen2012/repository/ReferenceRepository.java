package com.gmail.yauhen2012.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ReferenceRepository {

    int add(Connection connection, String item, String shop) throws SQLException;

    List<Integer> findAllStoreRelatedItemIds(Connection connection) throws SQLException;

    void delete(Connection connection, Integer id) throws SQLException;

}
