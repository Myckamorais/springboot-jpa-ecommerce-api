package com.myckael.ecommerce_api.repositories;

import com.myckael.ecommerce_api.entities.Category;
import com.myckael.ecommerce_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
