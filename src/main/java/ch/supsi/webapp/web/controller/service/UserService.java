package ch.supsi.webapp.web.controller.service;

import ch.supsi.webapp.web.model.Item;
import ch.supsi.webapp.web.model.ItemRepository;
import ch.supsi.webapp.web.model.User;
import ch.supsi.webapp.web.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> findById(String username) {
        return  userRepository.findById(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User findAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof String)
            return null; // anonymous

        UserDetails userDetails = (UserDetails) principal;
        return findUserByUsername(userDetails.getUsername());
    }

    public User findUserByUsername(String username) {
        return userRepository.findById(username).orElse(null);
    }
}
