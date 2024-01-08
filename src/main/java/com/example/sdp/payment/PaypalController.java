package com.example.sdp.payment;

import com.example.sdp.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PaypalController {

    @Autowired
    PaypalService service;

    @Autowired
    private CartItemService cartItemService; // Inject your CartItemService here

    public static final String SUCCESS_URL = "success";
    public static final String CANCEL_URL = "cancel";

//    @GetMapping("/pay")
//    public String payment(){
//        return "/Products/payment";
//    }
    @PostMapping("/pay")
    public String payment(@ModelAttribute("order") Order order) {
        try {
            // Save the order to the database
            cartItemService.saveOrder(order);

            // Create the PayPal payment
            Payment payment = service.createPayment(
                    order.getPrice(),
                    order.getCurrency(),
                    order.getMethod(),
                    order.getIntent(),
                    order.getDescription(),
                    "http://localhost:8080/" + CANCEL_URL, // Update the port to 8080
                    "http://localhost:8080/" + SUCCESS_URL  // Update the port to 8080
            );

            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/cancel")
    public String cancelPay() {
        return "/cancel";
    }

    @GetMapping("/success")
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "/success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }
}
