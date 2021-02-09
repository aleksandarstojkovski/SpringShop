package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.controller.service.CategoryService;
import ch.supsi.webapp.web.controller.service.ItemService;
import ch.supsi.webapp.web.controller.service.TypeService;
import ch.supsi.webapp.web.controller.service.UserService;
import ch.supsi.webapp.web.model.Item;
import ch.supsi.webapp.web.model.Role;
import ch.supsi.webapp.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    ItemService itemService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Autowired
    TypeService typeService;

    @Autowired
    ResourceLoader resourceLoader;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("/")
    public String root(Model model) {
        List<Item> items = itemService.findAll();
        List<Item> requests = itemService.findAll().stream().filter(item -> item.getType().getName().equalsIgnoreCase("Request")).collect(Collectors.toList());
        List<Item> offers = itemService.findAll().stream().filter(item -> item.getType().getName().equalsIgnoreCase("Offer")).collect(Collectors.toList());
        model.addAttribute("items", items);
        model.addAttribute("offers", offers);
        model.addAttribute("requests", requests);
        return "index";
    }


    @GetMapping("/item/{id}")
    public String getItem(@PathVariable int id, Model model) {
        Optional<Item> item = itemService.findById(id);
        if (item.isPresent()){
            model.addAttribute("isOwner",item.get().getAuthor() == userService.findAuthenticatedUser());
            model.addAttribute("item", item.get());
            return "itemDetails";
        }
        return "redirect:/404.html";
    }

    @GetMapping("/item/new")
    public String createItemPage(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userService.findById(userDetails.getUsername());
        Item item = new Item();
        user.ifPresent(item::setAuthor);

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("authors", userDetails);
        model.addAttribute("types", typeService.findAll());
        model.addAttribute("item", item);
        model.addAttribute("action","create");
        return "createItemForm";
    }

    @GetMapping("/register")
    public String registerGet(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(User user, Model model) {
        // user already exists
        if (userService.findById(user.getUsername()).isPresent()) {
            return "redirect:/register?error";
        }
        // user does not exist
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(new Role("ROLE_USER"));
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/item/{id}/edit")
    public String editItemPage(@PathVariable int id, Model model) {
        Optional<Item> item = itemService.findById(id);
        if (item.isPresent()){
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("authors", userService.findAll());
            model.addAttribute("types", typeService.findAll());
            model.addAttribute("item", item.get());
            model.addAttribute("action","edit");
            return "createItemForm";
        } else{
            return "redirect:/404.html";
        }
    }

    @PostMapping("/item/{id}/edit")
    public String editItem(@PathVariable int id, Item incomingItem, @RequestParam("file") MultipartFile file, Model model) throws IOException {
        Optional<Item> currentItem = itemService.findById(id);
        if (currentItem.isPresent()){
            if(!file.isEmpty()) {
                incomingItem.setImage(file.getBytes());
            } else {
                incomingItem.setImage(itemService.findById(incomingItem.getId()).get().getImage());
            }
            currentItem.get().edit(incomingItem);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("authors", userService.findAll());
            model.addAttribute("types", typeService.findAll());
            model.addAttribute("item", currentItem.get());
            itemService.save(incomingItem);
            return "redirect:/";
        } else{
            return "redirect:/404.html";
        }
    }

    @PostMapping("/item/new")
    public String createItem(Item item, @RequestParam("file") MultipartFile file, Model model) throws IOException {
        if(!file.isEmpty())
            item.setImage(file.getBytes());
        itemService.save(item);
        return "redirect:/";
    }

    @GetMapping("/item/{id}/delete")
    public String createItemPage(@PathVariable int id, Model model) {
        Optional<Item> optionalItem = itemService.findAll().stream().filter(item -> item.getId() == id).findFirst();
        optionalItem.ifPresent(item -> itemService.delete(item));
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping(value = "/item/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] image(@PathVariable int id) throws IOException {
        Optional<Item> item = itemService.findById(id);
        if (item.isPresent())
            if (item.get().getImage() == null){
                return Files.readAllBytes(resourceLoader.getResource("classpath:static/images/no_image.jpg").getFile().toPath());
            } else
                return item.get().getImage();
        else
            return null;
    }

}
