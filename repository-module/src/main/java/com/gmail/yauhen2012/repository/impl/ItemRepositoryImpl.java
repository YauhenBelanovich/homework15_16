package com.gmail.yauhen2012.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.gmail.yauhen2012.repository.ItemRepository;
import com.gmail.yauhen2012.repository.model.Item;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepositoryImpl extends GeneralRepositoryImpl<Item> implements ItemRepository {

    @Override
    public Item add(Connection connection, Item item) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement
                ("INSERT INTO item (name, description) VALUES (?,?)",
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating item failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setItemId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating item failed, no ID obtained.");
                }
            }
            return item;
        }
    }

    @Override
    public void delete(Connection connection, Integer id) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM item WHERE item_id=?"
                )
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

}