package com.example.shop_spring.services;

import com.example.shop_spring.models.Category;
import com.example.shop_spring.models.GroupCategory;
import com.example.shop_spring.repositories.GroupCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GroupCategoryService {
    private final GroupCategoryRepository groupCategoryRepository;

    public GroupCategoryService(GroupCategoryRepository groupCategoryRepository) {
        this.groupCategoryRepository = groupCategoryRepository;
    }

    public GroupCategory getGroupCategoryID(int id){
        Optional<GroupCategory> optionalGroupCategory = groupCategoryRepository.findById(id);
        return optionalGroupCategory.orElse(null);
    }

    public List<GroupCategory> getAllGroupCategory(){
        return groupCategoryRepository.findAll();
    }
    @Transactional
    public void saveGroupCategory(GroupCategory groupCategory) {
        groupCategoryRepository.save(groupCategory);
    }

    @Transactional
    public void updateGroupCategory(int id, GroupCategory groupCategory) {
        groupCategory.setId(id);
        groupCategoryRepository.save(groupCategory);
    }

    @Transactional
    public void deleteGroupCategory(int id) {
        groupCategoryRepository.deleteById(id);
    }
}
