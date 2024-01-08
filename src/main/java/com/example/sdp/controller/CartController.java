package com.example.sdp.controller;

import com.example.sdp.entity.CartItem;
import com.example.sdp.entity.Product;
import com.example.sdp.service.CartItemService;
import com.example.sdp.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    ProductService productService;


    @Autowired
    CartItemService cartItemService;

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable("id") int id, HttpSession session){

        System.out.println("user id:"+session.getAttribute("id")+" "+"product id:"+id);
      Product product= productService.findById(id);
        CartItem cartItem=new CartItem();
        cartItem.setName(product.getName());
        cartItem.setUserId((int)session.getAttribute("id"));
        cartItem.setCategory(product.getCategory());
        cartItem.setDescription(product.getDescription());
        cartItem.setPrice(product.getPrice());
        cartItemService.save(cartItem);

        return "redirect:/";

    }


    @GetMapping("/cart")
    public String cart(Model model,HttpSession session){
        List<CartItem> cartItems=cartItemService.getUserProducts((int)session.getAttribute("id"));
        double totalPrice = calculateTotalPrice(cartItems);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "/Products/Cart";
    }

    @GetMapping("/pay")
    public String payment(Model model, HttpSession session) {
        List<CartItem> cartItems = cartItemService.getUserProducts((int) session.getAttribute("id"));
        double totalPrice = calculateTotalPrice(cartItems);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);

        return "/Products/payment"; // Assuming your view name is "payment.html"
    }


    private double calculateTotalPrice(List<CartItem> cartItems) {
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {

                totalPrice += cartItem.getPrice();

        }
        return totalPrice;
    }

    @GetMapping("/removeFromCart/{id}")
    public String removeFromCart(@PathVariable("id") int id){
        CartItem cartItem=cartItemService.findById(id);
        cartItemService.removeFromCart(cartItem);
        return "redirect:/cart";
    }


}
