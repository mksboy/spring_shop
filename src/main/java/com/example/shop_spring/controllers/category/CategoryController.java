package com.example.shop_spring.controllers.category;

import com.example.shop_spring.services.CategoryService;
import com.example.shop_spring.services.GroupCategoryService;
import com.example.shop_spring.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final GroupCategoryService groupCategoryService;
    private final ProductService productService;

    @Autowired
    public CategoryController(CategoryService categoryService, GroupCategoryService groupCategoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.groupCategoryService = groupCategoryService;
        this.productService = productService;
    }

    @GetMapping("")
    public String getAllCategory(Model model){
        model.addAttribute("categories",groupCategoryService.getAllGroupCategory());
        model.addAttribute("groups",categoryService.getAllCategory());
        return "/category/category";
    }

    @GetMapping("/info/{id}")
    public String infoCategoryId(@PathVariable("id") int id, Model model) {
        model.addAttribute("category", categoryService.getCategoryID(id));
        model.addAttribute("group", groupCategoryService.getGroupCategoryID(id));

        return "/category/categoryInfo";
    }

    @GetMapping("/product/{id}")
    public String productCategory(@PathVariable("id") int id,Model model){
        model.addAttribute("productCategory",productService.getProductID(id));
        model.addAttribute("category", categoryService.getCategoryID(id));
        model.addAttribute("group", groupCategoryService.getGroupCategoryID(id));

        return "category/productGroup";
    }


}
