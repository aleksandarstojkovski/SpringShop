package ch.supsi.webapp.web.controller.service;

import ch.supsi.webapp.web.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeService {

    @Autowired
    TypeRepository typeRepository;

    public void save(Type user) {
        typeRepository.save(user);
    }

    public Optional<Type> findById(String type) {
        return  typeRepository.findById(type);
    }

    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    public void delete(Type user) {
        typeRepository.delete(user);
    }

}
