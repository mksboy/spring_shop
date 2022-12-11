package com.example.shop_spring.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title", nullable = false, columnDefinition = "text", unique = true)
    @NotEmpty(message = "Наименование товара не может быть пустым")
    private String title;
    @Column(name = "description", nullable = false, columnDefinition = "text")
    @NotEmpty(message = "Описание товара не может быть пустым")
    private String description;
    @Column(name = "price", nullable = false)
    @NotNull(message = "Цена товара не может быть пустым")
    @Min(value = 1, message = "Цена товара не может быть отрицатеельной или нулевой")
    private float price;
    @Column(name = "discount")
    private float discount;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "manufacturer", nullable = false)
    @NotEmpty(message = "Производитель не может быть пустым")
    private String manufacturer;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    private List<Image> imageList = new ArrayList<>();
    //---------------------------------------------------------------
    //Связь категории в таблице product
    @ManyToOne(optional = false) // Многие к одному
   private Category category;

    //---------------------------------------------------------------
    // создание корзины
    //---------------------------------------------------------------
    @ManyToMany()
    @JoinTable(name = "product_cart", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> personList;
    //---------------------------------------------------------------

    @OneToMany(mappedBy = "product")
    private List<Order> orderList;
    //---------------------------------------------------------------
    private LocalDateTime dateTime;
    //При создании обьекта класса заполняеся дата и время
    @PrePersist
    public void init() {
        dateTime = LocalDateTime.now();
    }
    //---------------------------------------------------------------

    // Метод по добавлению фотографий в лист к текущему продукту
    public void addImageToProduct(Image image) {
        image.setProduct(this);
        imageList.add(image);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Product(String title, String description, float price, float discount, Integer amount, String manufacturer) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.amount = amount;
        this.manufacturer = manufacturer;
    }

    public Product() {
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
