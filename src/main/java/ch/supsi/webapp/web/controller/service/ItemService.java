package ch.supsi.webapp.web.controller.service;

import ch.supsi.webapp.web.model.Item;
import ch.supsi.webapp.web.model.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public void save(Item item) {
        itemRepository.save(item);
    }

    public Optional<Item> findById(int id) {
        return  itemRepository.findById(id);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public void delete(Item item) {
        itemRepository.delete(item);
    }

    public List<Item> findAllByAuthor(String username) {
        return itemRepository.findAllByAuthor(username);
    }


}
