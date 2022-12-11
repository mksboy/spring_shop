package com.example.shop_spring.repositories;

import com.example.shop_spring.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// Integer в качестве первичного ключа
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    //Метод который возращает категорию по наименованию
    Category findByName(String name);
}
