package com.example.shop_spring.services;

import com.example.shop_spring.models.Person;
import com.example.shop_spring.repositories.PersonRepository;
import com.example.shop_spring.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Этот сервис работает в связке PersonDetails
//@Service аннотирует классы на уровне сервиса
@Service
//Сервис поможет получить пользователя по логину для аутентификации
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;
//Внедрение зависимости

    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    //Аутентификация -> получаем логин -> манипуляции
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Получаем пользователя из таблицы по логину с формы аутентификации
        Optional<Person> person = personRepository.findByLogin(username);
        //Если пользователь не был найден
        if (person.isEmpty()){
            //Выбрасываем исключение что данный пользлватель не найден
            //Данное исключение будет поймано Spring Security и сообщение будет выведено на страницу
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return new PersonDetails(person.get());
    }
}
