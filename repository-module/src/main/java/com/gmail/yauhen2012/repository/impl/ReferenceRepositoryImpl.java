package com.gmail.yauhen2012.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gmail.yauhen2012.repository.ReferenceRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ReferenceRepositoryImpl implements ReferenceRepository {

    @Override
    public int add(Connection connection, String item, String shop) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO references_between_item_shop (item_id, shop_id) VALUES " +
                                "((SELECT item_id FROM item WHERE name=?), " +
                                "(SELECT shop_id FROM shop WHERE name=?))"
                )
        ) {
            statement.setString(1, item);
            statement.setString(2, shop);
            return statement.executeUpdate();
        }
    }

    @Override
    public List<Integer> findAllStoreRelatedItemIds(Connection connection) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT item_id FROM references_between_item_shop GROUP BY item_id;"
                )
        ) {
            List<Integer> idsList = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    idsList.add(rs.getInt("item_id"));
                }
                return idsList;
            }
        }
    }

    @Override
    public void delete(Connection connection, Integer id) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM references_between_item_shop WHERE item_id=?"
                )
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

}
