package com.example.sdp.payment;

import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double price;
    private String currency;
    private String method;
    private String intent;
    private String description;

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public String getMethod() {
        return method;
    }

    public String getIntent() {
        return intent;
    }

    public String getDescription() {
        return description;
    }
}
