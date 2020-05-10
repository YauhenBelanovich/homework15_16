package com.gmail.yauhen2012.service;

import com.gmail.yauhen2012.service.model.AddItemDTO;

public interface ItemService {

    void addItem(AddItemDTO additemDto);

    int tieTogether(String itemName, String shopName);

    int removeItemsRelatedToTheShop();

}
