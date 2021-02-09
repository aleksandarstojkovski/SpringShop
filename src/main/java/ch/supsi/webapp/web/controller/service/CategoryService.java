package ch.supsi.webapp.web.controller.service;

import ch.supsi.webapp.web.model.Category;
import ch.supsi.webapp.web.model.CategoryRepository;
import ch.supsi.webapp.web.model.Item;
import ch.supsi.webapp.web.model.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public void save(Category item) {
        categoryRepository.save(item);
    }

    public Optional<Category> findById(String name) {
        return  categoryRepository.findById(name);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }

}
