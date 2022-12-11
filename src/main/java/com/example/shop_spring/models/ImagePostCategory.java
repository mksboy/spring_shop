package com.example.shop_spring.models;

import javax.persistence.*;

@Entity
public class ImagePostCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fileName;
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    private Category category;

    public ImagePostCategory(int id, String fileName, Category category) {
        this.id = id;
        this.fileName = fileName;
        this.category = category;
    }

    public ImagePostCategory() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
