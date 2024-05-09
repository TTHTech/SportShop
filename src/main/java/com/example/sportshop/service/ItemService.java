package com.example.sportshop.service;

import com.example.sportshop.model.Item;
import com.example.sportshop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    public void saveItem(Item item){
        this.itemRepository.save(item);
    }
    public Item getItemById(long id){
        return itemRepository.getItemById(id);
    }
    public void deleteById(long id){
        itemRepository.deleteById(id);
    }
}
