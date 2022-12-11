package com.example.shop_spring.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @ManyToOne (optional = false)
    private GroupCategory groupCategory;
    //======================================================================
    //Связь с продуктами
    @OneToMany(mappedBy = "category")
    private List<Product> products;
    //======================================================================
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "category")
    private List<ImagePostCategory> imagePostCategories = new ArrayList<>();
    //======================================================================
    public void addImageCategory(ImagePostCategory imagePostCategory){
//        image_group.setGroupCategory(this);
        imagePostCategory.setCategory(this);
//        imageListCategory.add(image_group);
        imagePostCategories.add(imagePostCategory);
    }
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

    public GroupCategory getGroup() {
        return groupCategory;
    }

    public void setGroup(GroupCategory group) {
        this.groupCategory = group;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    //============================================================================
    public List<ImagePostCategory> getImagePostCategories() {
        return imagePostCategories;
    }

    public void setImagePostCategories(List<ImagePostCategory> imagePostCategories) {
        this.imagePostCategories = imagePostCategories;
    }

    public GroupCategory getGroupCategory() {
        return groupCategory;
    }

    public void setGroupCategory(GroupCategory groupCategory) {
        this.groupCategory = groupCategory;
    }
}
