package com.gmail.yauhen2012.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gmail.yauhen2012.repository.ConnectionRepository;
import com.gmail.yauhen2012.repository.ItemDetailsRepository;
import com.gmail.yauhen2012.repository.ItemRepository;
import com.gmail.yauhen2012.repository.ReferenceRepository;
import com.gmail.yauhen2012.repository.model.Item;
import com.gmail.yauhen2012.repository.model.ItemDetails;
import com.gmail.yauhen2012.service.ItemService;
import com.gmail.yauhen2012.service.model.AddItemDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private ItemRepository itemRepository;
    private ConnectionRepository connectionRepository;
    private ItemDetailsRepository itemDetailsRepository;
    private ReferenceRepository referenceRepository;

    public ItemServiceImpl(ItemRepository itemRepository,
            ConnectionRepository connectionRepository, ItemDetailsRepository itemDetailsRepository, ReferenceRepository referenceRepository) {
        this.itemRepository = itemRepository;
        this.connectionRepository = connectionRepository;
        this.itemDetailsRepository = itemDetailsRepository;
        this.referenceRepository = referenceRepository;
    }

    @Override
    public void addItem(AddItemDTO addItemDto) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item toDatabaseItem = convertAddItemDTOToDatabaseItem(addItemDto);
                Item addedItem = itemRepository.add(connection, toDatabaseItem);
                int databaseItemId = addedItem.getItemId();
                toDatabaseItem.getItemDetails().setItemId(databaseItemId);
                itemDetailsRepository.add(connection, toDatabaseItem.getItemDetails());
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public int tieTogether(String itemName, String shopName) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int affectedRows = referenceRepository.add(connection, itemName, shopName);
                connection.commit();
                return affectedRows;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public int removeItemsRelatedToTheShop() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Integer> listIds = referenceRepository.findAllStoreRelatedItemIds(connection);
                int affectedRows = 0;
                for (Integer id : listIds) {
                    referenceRepository.delete(connection, id);
                    itemDetailsRepository.delete(connection, id);
                    itemRepository.delete(connection, id);
                    affectedRows++;
                }
                connection.commit();
                return affectedRows;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    private Item convertAddItemDTOToDatabaseItem(AddItemDTO addItemDto) {
        Item toDatabaseItem = new Item();
        toDatabaseItem.setName(addItemDto.getName());
        toDatabaseItem.setDescription(addItemDto.getDescription());

        ItemDetails itemDetails = new ItemDetails();
        itemDetails.setPrice(addItemDto.getPrice());

        toDatabaseItem.setItemDetails(itemDetails);
        return toDatabaseItem;
    }

}
