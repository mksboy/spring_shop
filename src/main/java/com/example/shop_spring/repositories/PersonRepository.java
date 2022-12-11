package com.example.shop_spring.repositories;
//Обращение к таблицы Person
import com.example.shop_spring.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository<Person, Integer> Person - это модель , Integer - тип данных первичного ключа
public interface PersonRepository extends JpaRepository<Person, Integer> {
    // метод находения человека по логину
    Optional<Person> findByLogin(String login);
}
