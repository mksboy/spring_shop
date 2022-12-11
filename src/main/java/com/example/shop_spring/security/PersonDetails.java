package com.example.shop_spring.security;

import com.example.shop_spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

// этот класс  получает информацию о Аутентифицированом пользователе
public class PersonDetails implements UserDetails {
    //Поле которое отвечает за модель
    private final Person person;
    //Внедряем модель через конструктор
    //@Autowired позволяет Spring разрешать и внедрять сотрудничающие компоненты в наш компонент
    @Autowired
    public PersonDetails(Person person) {
        this.person = person;
    }

// Этот метод может вернуть на роль пользователя
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(person.getRole())); // Возращает лист из одного элемента
    }
    // getPassword метод срабатывает при получении пароля  пользователя
    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getLogin();
    }
// Методы о состоянии аккаунта
    //Аккаунт действителен
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //Аккаунт не заблокирован
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //Пароль является действительным
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //Аккаунт является активным
    @Override
    public boolean isEnabled() {
        return true;
    }
    //Метод который возвращает обьект человека после успошной аутетинфикации
    public Person getPerson(){
        return this.person;
    }
}
