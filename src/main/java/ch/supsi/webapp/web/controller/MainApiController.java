package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.controller.service.ItemService;
import ch.supsi.webapp.web.controller.service.UserService;
import ch.supsi.webapp.web.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/items")
public class MainApiController {

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @RequestMapping(method= RequestMethod.GET, produces = {"application/json"})
    public List<Item> get() {
        return itemService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<Item> post(@RequestBody Item item){
        itemService.save(item);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<Item> getItem(@PathVariable int id) {
        Optional<Item> item = itemService.findById(id);
        return item.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value="/search", method=RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<List<Item>> getSearch(@RequestParam("q") String textToSearch) {
        List<Item> filteredItems = itemService.findAll().stream().filter(item -> item.getDescription().toLowerCase().contains(textToSearch.toLowerCase()) || item.getTitle().toLowerCase().contains(textToSearch.toLowerCase())).collect(Collectors.toList());
        return new ResponseEntity<>(filteredItems, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT, produces = {"application/json"})
    public ResponseEntity<Item> putItem(@PathVariable int id, @RequestBody Item incomingItem) {
        Optional<Item> currentItem = itemService.findById(id);
        if (currentItem.isPresent()){
            currentItem.get().setAuthor(incomingItem.getAuthor());
            currentItem.get().setDescription(incomingItem.getDescription());
            currentItem.get().setTitle(incomingItem.getTitle());
            return new ResponseEntity<>(currentItem.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE, produces = {"application/json"})
    public ResponseEntity<Object> deleteItem(@PathVariable int id) {
        HashMap<String, Object> returnJson = new HashMap<>();
        Optional<Item> currentItem = itemService.findById(id);
        HttpStatus httpStatus;
        if (currentItem.isPresent()){
            itemService.delete(currentItem.get());
            returnJson.put("success", true);
            httpStatus = HttpStatus.OK;
        } else {
            returnJson.put("success", false);
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnJson,httpStatus);
    }

    @RequestMapping(value="/find/{username}", method=RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<List<Item>> test(@PathVariable String username) {
        HttpStatus httpStatus;
        if (userService.findById(username).isPresent()){
            return new ResponseEntity<>(itemService.findAllByAuthor(username),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<Item>(),HttpStatus.NOT_FOUND);
    }

}
