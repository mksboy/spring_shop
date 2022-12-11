//Создание запроса который выдет на /index
package com.example.shop_spring.controllers.user;

import com.example.shop_spring.ennum.Status;
import com.example.shop_spring.models.Cart;
import com.example.shop_spring.models.Order;
import com.example.shop_spring.models.Product;
import com.example.shop_spring.repositories.CartRepository;
import com.example.shop_spring.repositories.OrderRepository;
import com.example.shop_spring.security.PersonDetails;
import com.example.shop_spring.services.CategoryService;
import com.example.shop_spring.services.GroupCategoryService;
import com.example.shop_spring.services.PersonService;
import com.example.shop_spring.services.ProductService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



//---------------------------------------------------------------------------------------------
// Указывает, что аннотированный класс является "Контроллером" (например, веб-контроллером).
//Эта аннотация служит специализацией@Component, позволяющей автоматически определять классы реализации посредством сканирования путей к классам. Обычно он используется в сочетании с аннотированными методами обработчика, основанными на RequestMapping аннотации.
@Controller
public class UserController {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    private final ProductService productService;
    private final PersonService personService;
    private final GroupCategoryService groupCategoryService;
    private final CategoryService categoryService;

    public UserController(OrderRepository orderRepository, CartRepository cartRepository, ProductService productService, PersonService personService, GroupCategoryService groupCategoryService, CategoryService categoryService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productService = productService;

        this.personService = personService;
        this.groupCategoryService = groupCategoryService;
        this.categoryService = categoryService;
    }

    @GetMapping("/index")
    public String index(Model model) {

        // Получаем объект аутентификации - > с помощью Spring SecurityContextHolder обращаемся к контексту и на нем вызываем метод аутентификации.
        // Из потока для текущего пользователя мы получаем объект, который был положен в сессию после аутентификации
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        String role = personDetails.getPerson().getRole();
        if(role.equals("ROLE_ADMIN"))
        {
            return "redirect:/admin";
        }
        model.addAttribute("person", personService.getAllPerson());
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("category",groupCategoryService.getAllGroupCategory());
        return "user/index";
    }

    @GetMapping("/cart/add/{id}")
    public String addProductInCart(@PathVariable("id") int id, Model model){
        Product product = productService.getProductID(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        Cart cart = new Cart(id_person, product.getId());
        cartRepository.save(cart);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cart(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        List<Cart> cartList = cartRepository.findByPersonId(id_person);
        List<Product> productsList = new ArrayList<>();
        for (Cart cart: cartList) {
            productsList.add(productService.getProductID(cart.getProductId()));
        }

        float price = 0;
        for (Product product: productsList) {
            price += product.getPrice();
        }
        model.addAttribute("price", price);
        model.addAttribute("cart_product", productsList);
        return "user/cart";
    }

    @GetMapping("/cart/delete/{id}")
    public String deleteProductCart(Model model, @PathVariable("id") int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        cartRepository.deleteCartByProductId(id);
        return "redirect:/cart";
    }

    @GetMapping("/order/create")
    public String order(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        List<Cart> cartList = cartRepository.findByPersonId(id_person);
        List<Product> productsList = new ArrayList<>();
        // Получаем продукты из корзины по id
        for (Cart cart: cartList) {
            productsList.add(productService.getProductID(cart.getProductId()));
        }

        float price = 0;
        for (Product product: productsList){
            price += product.getPrice();
        }

        String uuid = UUID.randomUUID().toString();
        for (Product product: productsList){
            Order newOrder = new Order(uuid, product, personDetails.getPerson(), 1, product.getPrice(), Status.Ожидает);
            orderRepository.save(newOrder);
            cartRepository.deleteCartByProductId(product.getId());
        }
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String ordersUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        List<Order> orderList = orderRepository.findByPerson(personDetails.getPerson());
        model.addAttribute("orders", orderList);
        return "/user/orders";
    }
}
