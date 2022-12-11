package com.example.shop_spring.services;

import com.example.shop_spring.models.Product;
import com.example.shop_spring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //Данный метод позволяет вернуть все продукты
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    //Данный метод позволяет вернуть товар по ID
    public Product getProductID(int id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    @Transactional
    //Данный метод позволяет сохранить товар
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional
// Данный метод позволяет обновить данные о продукте
    public void updateProduct(int id, Product product) {
        product.setId(id);
        productRepository.save(product);
    }
@Transactional
    // Данный метод позволяет удалить товар по ID
    public void deleteProduct(int id){
       productRepository.deleteById(id);
    }
}
