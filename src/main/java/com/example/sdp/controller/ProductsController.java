package com.example.sdp.controller;

import com.example.sdp.entity.Product;
import com.example.sdp.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.List;

@Controller
//@RequestMapping("/products")
public class ProductsController {

    private final ProductService productService;

    public  ProductsController(ProductService productService) {
        this.productService = productService;
    }



    @GetMapping("/dairy")
    public String getDairyProducts(Model model) {
        // Fetch the list of products with the category "vegetables" from the database
        List<Product> dairyProducts = productService.findByCategory("Dairy Products");

        // Add the list of vegetable products to the model for Thymeleaf
        model.addAttribute("dairyProducts", dairyProducts);

        // Return the Thymeleaf template name for displaying vegetable products
        return "/Products/dairy";
    }
    @GetMapping("/fruits")
    public String getFruitsProducts(Model model,HttpSession session) {
        // Fetch the list of products with the category "vegetables" from the database
        List<Product> fruitProducts = productService.findByCategory("Fruits");

        // Add the list of vegetable products to the model for Thymeleaf
        model.addAttribute("fruitProducts", fruitProducts);

        // Return the Thymeleaf template name for displaying vegetable products
        return "/Products/fruits";
    }


    @GetMapping("/addProduct")
    public String addProductForm(Model model, HttpSession session) {
        // Get the user's role from the session attribute
        String userRole = (String) session.getAttribute("userRole");

        // Check if the user has the "ADMIN" role
        if (userRole != null && userRole.equals("Admin")) {
            model.addAttribute("product", new Product());
            return "/Products/addProduct";
        } else {
            // Handle unauthorized access (e.g., redirect to an error page)
            return "redirect:/"; // You can create an "unauthorized" page or URL
        }
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute Product product) throws IOException {


        // Save the product to the database and get the saved product
        productService.saveProduct(product);

        // Set the saved product back to the model to display on the front end
        // This allows you to access the saved product's ID or any other generated fields
        //Model.addAttribute("product", product);
        return "redirect:/";
    }


    @GetMapping("/vegetables")
    public String getVegetableProducts(Model model) {
        // Fetch the list of products with the category "vegetables" from the database
        List<Product> vegetableProducts = productService.findByCategory("Vegetables");

        // Add the list of vegetable products to the model for Thymeleaf
        model.addAttribute("vegetableProducts", vegetableProducts);

        // Return the Thymeleaf template name for displaying vegetable products
        return "/Products/vegetablesProducts";
    }

    @GetMapping("/editProduct/{id}")
    public String showEditProductForm(@PathVariable int id, Model model,HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        // Check if the user has the "ADMIN" role
        if (userRole != null && userRole.equals("Admin")) {
            Product product = productService.findById(id);
            model.addAttribute("product", product);
            return "/Products/editProduct";
        } else {
            // Handle unauthorized access (e.g., redirect to an error page)
            return "redirect:/"; // You can create an "unauthorized" page or URL
        }

    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute("product") Product product) {
        productService.updateProduct(product);
        return "redirect:/"; // Redirect to a page showing the updated product list
    }


    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable int id) {
        Product product = productService.findById(id);
        productService.delete(product);
        return "redirect:/";
    }

    @GetMapping("/payment-gateway")
    public String paymentGateway(){
        return "/Products/payment";
    }
//    @PostMapping("/selectRegion")
//    public void storeRegion(@RequestParam String region, HttpSession session) {
//        session.setAttribute("selectedRegion", region);
//    }

//    @GetMapping("/cart")
//    public String cart(){
//        return "/Products/Cart";
//    }

}
