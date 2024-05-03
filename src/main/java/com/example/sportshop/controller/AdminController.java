package com.example.sportshop.controller;

import com.example.sportshop.model.Category;
import com.example.sportshop.model.Product;
import com.example.sportshop.service.CategoryService;
import com.example.sportshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public AdminController(CategoryService categoryService,ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/admin")
    public String adminHomePage() {
        return "admin/index";
    }

    @GetMapping("/product")
    public String adminProductPage(Model model) {
        List<Product> products = productService.getAllProduct();
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);

        return "admin/product";
    }

    @PostMapping("/delete")
    public String handleDeleteProduct(@RequestParam("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/product";
    }

    @GetMapping("/create")
    public String handleCreatProduct(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/create";
    }
    @PostMapping("/update")
    public String handleUpdateProduct(@ModelAttribute Product product) {
        Product currentProduct = productService.getProductById(product.getId());
        currentProduct.setName(product.getName());
        currentProduct.setPrice(product.getPrice());
        productService.saveProduct(currentProduct);
        return "redirect:/product";
    }
}
