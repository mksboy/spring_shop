package com.example.shop_spring.services;

import com.example.shop_spring.models.Person;
import com.example.shop_spring.models.Product;
import com.example.shop_spring.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    //Внедрение зависимости
    @Autowired
    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;

    }

    //Поиск пользователя по логину
    public Person findByLogin(Person person) {
        Optional<Person> person_db = personRepository.findByLogin(person.getLogin());
        return person_db.orElse(null);
    }
@Transactional
public Person getPersonID(int id) {
    Optional<Person> optionalPerson = personRepository.findById(id);
    return optionalPerson.orElse(null);
}

    @Transactional
    //Специальй метод  регистрации для сохранения обьекта
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        personRepository.save(person);
    }

    @Transactional

    //Специальй метод  регистрации для сохранения обьекта
    public void saveRoleID(int id,Person person) {
        person.setId(id);
        personRepository.save(person);
    }



    @Transactional
    public void updatePerson(Integer id,Person person) {
        person.setId(id);
        personRepository.save(person);
    }


    @Transactional
    public void deletePerson(int id) {
        personRepository.deleteById(id);
    }

    @Transactional
    public List<Person> getAllPerson() {
      return personRepository.findAll();
    }


}
