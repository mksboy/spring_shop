package com.example.shop_spring.services;

import com.example.shop_spring.models.Order;
import com.example.shop_spring.models.Product;
import com.example.shop_spring.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    //Данный метод позволяет вернуть товар по ID
    public Order getOrderID(int id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    @Transactional
    //Данный метод позволяет сохранить товар
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Transactional
    //Данный метод позволяет сохранить товар
    public void saveOrderID(int id,Order order) {
        order.setId(id);
        orderRepository.save(order);
    }

    @Transactional
// Данный метод позволяет обновить данные о продукте
    public void updateOrder(int id, Order order) {
        order.setId(id);
        orderRepository.save(order);
    }
    @Transactional
    // Данный метод позволяет удалить товар по ID
    public void deleteOrder(int id){
        orderRepository.deleteById(id);
    }
}
