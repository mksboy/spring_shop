package com.example.shop_spring.models;

import javax.persistence.*;

@Entity
public class ImageCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fileName;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private GroupCategory groupCategory;

    public ImageCategory(int id, String fileName, GroupCategory groupCategory) {
        this.id = id;
        this.fileName = fileName;
        this.groupCategory = groupCategory;
    }

    public ImageCategory() {
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

    public GroupCategory getGroupCategory() {
        return groupCategory;
    }

    public void setGroupCategory(GroupCategory groupCategory) {
        this.groupCategory = groupCategory;
    }
}