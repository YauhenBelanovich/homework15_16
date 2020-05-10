package com.gmail.yauhen2012.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.gmail.yauhen2012.repository.ShopRepository;
import com.gmail.yauhen2012.repository.model.Shop;
import org.springframework.stereotype.Repository;

@Repository
public class ShopRepositoryImpl extends GeneralRepositoryImpl<Shop> implements ShopRepository {

    @Override
    public Shop add(Connection connection, Shop shop) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement
                ("INSERT INTO shop (name, location) VALUES (?,?)",
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, shop.getName());
            statement.setString(2, shop.getLocation());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating shop failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    shop.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating shop failed, no ID obtained.");
                }
            }
            return shop;
        }
    }

    @Override
    public void delete(Connection connection, Integer id) throws SQLException {

    }

}
