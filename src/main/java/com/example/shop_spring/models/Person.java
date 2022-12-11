package com.example.shop_spring.models;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

//@Entity — Указывает, что данный бин (класс) является сущностью.
@Entity
//@Table — указывает на имя таблицы, которая будет отображаться в этой сущности
@Table(name = "Person")
public class Person {
    //@Id помечает поле в классе модели как первичный ключ
    @Id
    //@Column — указывает на имя колонки, которая отображается в свойство сущности.
    @Column(name = "id")
    //Этот вид стратегии генерации первичного ключа обычно известен как саморазвитие первичного ключа. Когда база данных вставляет данные, она автоматически присваивает значение первичному ключу.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //Валидация
    @NotEmpty(message = "Логин не может быть пустым")
    @Size(min = 5, max = 100, message = "Логин должен быть от 5 до 100 символов")
    @Column(name = "login")
    private String login;

    @NotEmpty(message = "Пароль не может быть пустым")
//    @Size(min = 8, max = 100, message = "Пароль должен быть от 5 до 100 символов")
    @Column(name = "password")
//    @Max(value = 100,message = "Пароль должен быть меньше 100 символов")
    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "Строчные и прописные латинские буквы, цифры, спецсимволы. Минимум 8 символов")
    private String password;

    @Column(name = "role")
    private String role;
    //---------------------------------------------------
// Связь многие ко многим для создания корзины
    // product_cart имя новой таблицы
    // в этой табице будет поле person_id и product_id
    //
    //---------------------------------------------------
    @ManyToMany()
    @JoinTable(name = "product_cart", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> product;
    //---------------------------------------------------

    @OneToMany(mappedBy = "person")
    private List<Order> orderList;

    public Person() {
    }



    public Person(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public Person(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public Person(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
