package com.example.shop_spring.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GroupCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "groupCategory")
    private List<Category> categories;
//======================================================================
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "groupCategory")
    private List<ImageCategory> imageListCategory = new ArrayList<>();
//======================================================================
    public void addImageGroupCategory(ImageCategory image_group){
        image_group.setGroupCategory(this);
        imageListCategory.add(image_group);
    }
//======================================================================

//======================================================================
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<ImageCategory> getImageListCategory() {
        return imageListCategory;
    }

    public void setImageListCategory(List<ImageCategory> imageListCategory) {
        this.imageListCategory = imageListCategory;
    }
}
