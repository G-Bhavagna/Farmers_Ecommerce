//package com.example.sdp.entity;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.*;
//
//import java.util.*;
//
//@Entity
//public class ShoppingCart {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @ManyToOne
//    private Users user;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
//    private List<CartItem> cartItems = new ArrayList<>();
//
//    public ShoppingCart() {
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public Users getUser() {
//        return user;
//    }
//
//    public void setUser(Users user) {
//        this.user = user;
//    }
//
//    public List<CartItem> getCartItems() {
//        return cartItems;
//    }
//
//    public void setCartItems(List<CartItem> cartItems) {
//        this.cartItems = cartItems;
//    }
//}
