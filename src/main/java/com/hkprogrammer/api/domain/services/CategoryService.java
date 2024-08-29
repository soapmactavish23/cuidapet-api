package com.hkprogrammer.api.domain.services;

import com.hkprogrammer.api.domain.models.Category;
import com.hkprogrammer.api.domain.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

}
