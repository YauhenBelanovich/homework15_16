package com.gmail.yauhen2012.springbootmodule.runner;

import java.lang.invoke.MethodHandles;

import com.gmail.yauhen2012.service.ShopService;
import com.gmail.yauhen2012.service.ItemService;
import com.gmail.yauhen2012.service.model.AddItemDTO;
import com.gmail.yauhen2012.service.model.AddShopDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private ItemService itemService;
    private ShopService shopService;

    public MyApplicationRunner(ItemService itemService, ShopService shopService) {
        this.itemService = itemService;
        this.shopService = shopService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        AddItemDTO addItemDto = new AddItemDTO();
        addItemDto.setName("item1");
        addItemDto.setDescription("description1");
        addItemDto.setPrice(30);

        AddShopDTO addShopDto = new AddShopDTO();
        addShopDto.setName("shop1");
        addShopDto.setLocation("location1");

        shopService.addShop(addShopDto);
        itemService.addItem(addItemDto);

        AddItemDTO addItemDto2 = new AddItemDTO();
        addItemDto2.setName("item2");
        addItemDto2.setDescription("description2");
        addItemDto2.setPrice(50);

        AddShopDTO addShopDto2 = new AddShopDTO();
        addShopDto2.setName("shop2");
        addShopDto2.setLocation("location2");

        shopService.addShop(addShopDto2);
        itemService.addItem(addItemDto2);

        AddItemDTO addItemDto3 = new AddItemDTO();
        addItemDto3.setName("item3");
        addItemDto3.setDescription("description3");
        addItemDto3.setPrice(50);

        AddShopDTO addShopDto3 = new AddShopDTO();
        addShopDto3.setName("shop3");
        addShopDto3.setLocation("location3");
        itemService.addItem(addItemDto3);
        shopService.addShop(addShopDto3);

        int ref1 = itemService.tieTogether("item1", "shop2");
        int ref2 = itemService.tieTogether("item1", "shop3");
        logger.info("Connections established: " + (ref1 + ref2));

        int deletedRows = itemService.removeItemsRelatedToTheShop();
        logger.info("Items removed: " + deletedRows);
    }

}
