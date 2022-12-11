package com.example.shop_spring.controllers.admin;

import com.example.shop_spring.ennum.Status;
import com.example.shop_spring.models.*;
import com.example.shop_spring.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Value("${upload.path}")
    private String uploadPath;


    private final ProductService productService;
    private final CategoryService categoryService;
    private final GroupCategoryService groupCategoryService;
    private final PersonService personService;
    private final PersonDetailsService personDetailsService;
    private final RoleService roleService;
    private final OrderService orderService;



    @Autowired
    public AdminController(ProductService productService, CategoryService categoryService, GroupCategoryService groupCategoryService, PersonService personService, PersonDetailsService personDetailsService, RoleService roleService, OrderService orderService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.groupCategoryService = groupCategoryService;
        this.personService = personService;
        this.personDetailsService = personDetailsService;
        this.roleService = roleService;
        this.orderService = orderService;
    }

    @GetMapping("")
    public String admin(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("category",categoryService.getAllCategory());
        return "admin/admin";
    }


    //http:localhost:8080/admin/product/add
    //Метод по отбражению страницы с возможностью добавления товаров
    @GetMapping("/product/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryService.getAllCategory());
        return "product/addProduct";
    }


    // Метод по добавлению продукта в БД через сервис->репозиторий
    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one") MultipartFile file_one, @RequestParam("file_two") MultipartFile file_two, @RequestParam("file_three") MultipartFile file_three, @RequestParam("file_four") MultipartFile file_four, @RequestParam("file_five") MultipartFile file_five) throws IOException {
        if (bindingResult.hasErrors()) {
            return "product/addProduct";
        }
        // 1
        if (file_one != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
            file_one.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        // 2
        if (file_two != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_two.getOriginalFilename();
            file_two.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            // изображение привязано к продукту
            image.setProduct(product);
            //Наименование фотографии в папке
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        // 3
        if (file_three != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_three.getOriginalFilename();
            file_three.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            // изображение привязано к продукту
            image.setProduct(product);
            //Наименование фотографии в папке
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        // 4
        if (file_four != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_four.getOriginalFilename();
            file_four.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            // изображение привязано к продукту
            image.setProduct(product);
            //Наименование фотографии в папке
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        // 5
        if (file_five != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_five.getOriginalFilename();
            file_five.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            // изображение привязано к продукту
            image.setProduct(product);
            //Наименование фотографии в папке
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        //----------------------------------------------------------------------
        productService.saveProduct(product);
        return "redirect:/admin";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        categoryService.deleteCategory(id);
        return "redirect:/admin";
    }

    @GetMapping("/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") int id) {
        model.addAttribute("product", productService.getProductID(id));
        model.addAttribute("category", categoryService.getAllCategory());
        return "product/editProduct";
    }

    @PostMapping("/product/edit/{id}")
    public String editProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "product/editProduct";
        }
        productService.updateProduct(id, product);
        return "redirect:/admin";
    }

    //====================================================
    @GetMapping("product/add/group")
    public String addGroup(Model model) {
        model.addAttribute("group", new GroupCategory());
        model.addAttribute("category_post", groupCategoryService.getAllGroupCategory());
        model.addAttribute("groups",categoryService.getAllCategory());
        return "redirect:/admin";
    }

    @PostMapping("product/add/group")
    public String addGroup(@ModelAttribute("group") @Valid GroupCategory groupCategory, BindingResult bindingResult, @RequestParam("file_img_one") MultipartFile file_img_one) throws IOException {
        if (bindingResult.hasErrors()) {
            return "product/addCategory";
        }
        // 1
        if (file_img_one != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_img_one.getOriginalFilename();
            file_img_one.transferTo(new File(uploadPath + "/" + resultFileName));

            ImageCategory imageCategory = new ImageCategory();
            imageCategory.setGroupCategory(groupCategory);
            imageCategory.setFileName(resultFileName);
            groupCategory.addImageGroupCategory(imageCategory);
        }
        groupCategoryService.saveGroupCategory(groupCategory);
        return "redirect:/admin";
    }

    //====================================================
    // Подгруппы
    //====================================================
    @GetMapping("product/add/group/category")
    public String addGroupCategory(Model model) {
        model.addAttribute("category_new", new Category());
        model.addAttribute("category_post", groupCategoryService.getAllGroupCategory());
        model.addAttribute("groups",categoryService.getAllCategory());
        //      model.addAttribute("category_post", categoryService.getAllCategory());
        return "product/addPostCategory";
    }

    //
//@PostMapping("product/add/group/category")
//public String addGroupCategory(@ModelAttribute("category_new") Category category){
//    categoryService.saveCategory(category);
//    return "redirect:/admin";
//}
    //----------------------------------------------------
    @PostMapping("product/add/group/category")
    public String addGroupCategory(@ModelAttribute("category_new") Category category, BindingResult bindingResult, @RequestParam("file_img_two") MultipartFile file_img_two) throws IOException {
        if (bindingResult.hasErrors()) {
            return "product/addPostCategory";
        }
        // 1
        if (file_img_two != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_img_two.getOriginalFilename();
            file_img_two.transferTo(new File(uploadPath + "/" + resultFileName));

//            ImageCategory imageCategory = new ImageCategory();
            ImagePostCategory imagePostCategory = new ImagePostCategory();
//            imageCategory.setGroupCategory(groupCategory);
            imagePostCategory.setCategory(category);
//            imageCategory.setFileName(resultFileName);
            imagePostCategory.setFileName(resultFileName);
//            groupCategory.addImageGroupCategory(imageCategory);
            category.addImageCategory(imagePostCategory);
        }
//        groupCategoryService.saveGroupCategory(groupCategory);
        categoryService.saveCategory(category);
        return "redirect:/admin";
    }

    //======================================================================
    //====================================================
    @GetMapping("role")
    public String addRole(Model model) {
        model.addAttribute("role_get", new Role());

        return "admin/addRole";
    }

    @PostMapping("/role")
    public String addRole(@ModelAttribute("role_post") Role role,BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/addRole";
        } else {
            roleService.saveRole(role);
            return "redirect:/admin";
        }
    }
    //====================================================
    //====================================================
    @GetMapping("/person")
    public String editRole(Model model) {
        model.addAttribute("all_person",personService.getAllPerson());
        return "admin/person";
    }

    @GetMapping("/person/{id}")
    public String editPersonId(Model model, @PathVariable("id") int id ) {
        model.addAttribute("person", personService.getPersonID(id));
       model.addAttribute("role_id", roleService.getAllRole());
 //       model.addAttribute("person", person);
        return "admin/editRole";
    }

    @PostMapping("/person/{id}")
    public String editPersonId(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
       personService.saveRoleID(id,person);

        return "redirect:/admin";
    }

 @PostMapping("/admin")
    public String exitCategory(){
     return "redirect:/admin";
 }


 //-------------------------------------------------------
 @GetMapping("/order")
 public String editOrder(Model model) {

     model.addAttribute("all_order",orderService.getAllOrder());
     return "admin/editOrder";
 }

    @GetMapping("/order/{id}")
    public String editOrder(Model model, @PathVariable("id") int id ) {
        model.addAttribute("orderID",orderService.getOrderID(id));
        //       model.addAttribute("person", person);
        return "admin/viewOrder";
    }

    @PostMapping("/order/{id}")
    public String editOrder(@ModelAttribute("person") Order order, @PathVariable("id") int id) {
        orderService.saveOrderID(id,order);

        return "redirect:/admin";
    }
}

