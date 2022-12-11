package com.example.shop_spring.controllers;

import com.example.shop_spring.models.Person;
import com.example.shop_spring.services.CategoryService;
import com.example.shop_spring.services.GroupCategoryService;
import com.example.shop_spring.services.PersonService;
import com.example.shop_spring.services.ProductService;
import com.example.shop_spring.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
//Данный контроллер будет срабатывать при /authentication
@RequestMapping("/authentication")
public class AuthenticationController {
    private final PersonValidator personValidator;
    private  final PersonService personService;
    private final GroupCategoryService groupCategoryService;
    private final CategoryService categoryService;
    private final ProductService productService;

@Autowired
    public AuthenticationController(PersonValidator personValidator, PersonService personService, GroupCategoryService groupCategoryService, CategoryService categoryService, ProductService productService) {
        this.personValidator = personValidator;
        this.personService = personService;
    this.groupCategoryService = groupCategoryService;
    this.categoryService = categoryService;
    this.productService = productService;
}

    @GetMapping("/login")
    public String login(){
        return "authentication/login";
    }

    @GetMapping("/index")
    public String index(Model model){
    model.addAttribute("category",groupCategoryService.getAllGroupCategory());
    model.addAttribute("group_category", categoryService.getAllCategory());
    model.addAttribute("products",productService.getAllProduct());
    return "authentication/index";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("person") Person person){
        return "authentication/registration";
    }

    @PostMapping("/registration")
    public String resultRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person,bindingResult);// Если валидатор возвращает ошибку помещаем данную ошибку в bindingResult
        if (bindingResult.hasErrors()){
            return "authentication/registration";
        }
        personService.register(person);
        return "redirect:/index";
    }
}
