package com.hkprogrammer.api.domain.repositories;

import com.hkprogrammer.api.domain.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
