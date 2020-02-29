package com.gmail.yauhen2012.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gmail.yauhen2012.repository.ItemDetailsRepository;
import com.gmail.yauhen2012.repository.model.ItemDetails;
import org.springframework.stereotype.Repository;

@Repository
public class ItemDetailsRepositoryImpl extends GeneralRepositoryImpl<ItemDetails> implements ItemDetailsRepository {

    @Override
    public ItemDetails add(Connection connection, ItemDetails itemDetails) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO item_details(item_id, price) VALUES (?,?)"
                )
        ) {
            statement.setInt(1, itemDetails.getItemId());
            statement.setInt(2, itemDetails.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user information failed, no rows affected.");
            }
            return itemDetails;
        }
    }

    @Override
    public void delete(Connection connection, Integer id) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM item_details WHERE item_id=?"
                )
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

}
