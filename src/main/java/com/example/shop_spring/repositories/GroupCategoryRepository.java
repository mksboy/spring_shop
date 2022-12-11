package com.example.shop_spring.repositories;

import com.example.shop_spring.models.GroupCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupCategoryRepository extends JpaRepository<GroupCategory, Integer> {
    GroupCategory findByName(String name);
}
