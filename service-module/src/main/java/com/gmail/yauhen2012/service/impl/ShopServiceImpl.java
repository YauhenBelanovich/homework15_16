package com.gmail.yauhen2012.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.yauhen2012.repository.ConnectionRepository;
import com.gmail.yauhen2012.repository.ShopRepository;
import com.gmail.yauhen2012.repository.model.Shop;
import com.gmail.yauhen2012.service.ShopService;
import com.gmail.yauhen2012.service.model.AddShopDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private ConnectionRepository connectionRepository;
    private ShopRepository shopRepository;

    public ShopServiceImpl(ConnectionRepository connectionRepository,
            ShopRepository shopRepository) {
        this.connectionRepository = connectionRepository;
        this.shopRepository = shopRepository;
    }

    @Override
    public void addShop(AddShopDTO addShopDto) {

        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Shop toDatabaseShop = convertShopDTOToDatabaseShop(addShopDto);
                Shop addedShop = shopRepository.add(connection, toDatabaseShop);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private Shop convertShopDTOToDatabaseShop(AddShopDTO addShopDto) {
        Shop toDatabaseShop = new Shop();
        toDatabaseShop.setName(addShopDto.getName());
        toDatabaseShop.setLocation(addShopDto.getLocation());
        return toDatabaseShop;
    }

}
