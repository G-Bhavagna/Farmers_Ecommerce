package com.example.sdp.controller;


import com.example.sdp.entity.Users;
import com.example.sdp.service.ProductService;
import com.example.sdp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;
    ProductService productService;
    public UserController(UserService userService,ProductService productService) {
        this.productService=productService;
        this.userService=userService;
    }



    @GetMapping("/")
    public String home(){

        return "index";
    }

    @GetMapping("/register")
    public String loginForm(Model model){
        model.addAttribute("users",new Users());
        return "register";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("users")Users users){
        userService.save(users);
        return "redirect:/login";
    }

    @GetMapping("/usersPage")
    public String usersPage(){
        return "usersPage";
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        // Check if the user is already logged in (you can use a session attribute)
        if (session.getAttribute("user") != null) {
            return "redirect:/userPage"; // Redirect logged-in users to the home page
        }
        return "login";
    }

    @PostMapping("/loginInfo")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        // Check if the username and password match a user in the database
        Users user = userService.findByUsername(username, password);

        if (user != null) {
            // Authentication successful, store user details in the session
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userRole", user.getRole()); // Assuming your User entity has a role field
            session.setAttribute("id",user.getId());
            return "redirect:/"; // Redirect to the dashboard or a secure page
        } else {
            // Authentication failed, display an error message
            return "login";
        }
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        // Clear the session to log out the user
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/editProfile")
    public String editProfile(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        Users user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "editProfile";
    }

    @PostMapping("/editProfile")
    public String updateProfile(@ModelAttribute("user") Users updatedUser, HttpSession session) {
        // Perform the update operation based on the data in updatedUser
        // Make sure to validate and handle errors appropriately
        String username = (String) session.getAttribute("username");
        Users currentUser = userService.findByUsername(username);

        // Update the relevant fields of currentUser with values from updatedUser
        currentUser.setUsername(updatedUser.getUsername());
        currentUser.setEmail(updatedUser.getEmail());
        currentUser.setPassword(updatedUser.getPassword());

        userService.save(currentUser);

        // Redirect to the user profile page after a successful update
        return "redirect:/editProfile";
    }

}





