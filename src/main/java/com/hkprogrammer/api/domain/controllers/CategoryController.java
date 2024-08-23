package com.hkprogrammer.api.domain.controllers;

import com.hkprogrammer.api.domain.models.Category;
import com.hkprogrammer.api.domain.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/categorias")
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public List<Category> findAll() {
        return service.findAll();
    }

}
