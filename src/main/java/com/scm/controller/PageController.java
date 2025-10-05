package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.scm.entity.User;
import com.scm.form.UserForm;
import com.scm.helper.Message;
import com.scm.service.UserServiceImpl;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to the SCM Application!");
        model.addAttribute("name", "SCM User");
        return "home"; // This should return the name of the view to be rendered
    }

    @GetMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("loginFlag", "false");
        model.addAttribute("message", "Logged in.");
        return "about"; // This should return the name of the view to be rendered
    }

    @GetMapping("/services")
    public String servicePage() {
        return "services"; // This should return the name of the view to be rendered
    }

    @GetMapping("/contacts")
    public String contactPage() {
        return "contacts"; // This should return the name of the view to be rendered
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // This should return the name of the view to be rendered
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        // We use a UserForm object here (instead of the User entity) to:
        // 1. Separate form data from the database entity for security and validation.
        // 2. Prevent accidental binding of sensitive or unwanted fields.
        // 3. Allow flexibility to add or remove form fields without affecting the User
        // entity.
        // 4. Follow best practices for DTO (Data Transfer Object) usage in Spring MVC.
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register"; // This should return the name of the view to be rendered
    }

    // processing Register form
    @PostMapping("/do-register")
    public String processRegister(@ModelAttribute UserForm userForm,HttpSession session) {
        // fetch the form data--> we will make a class to store the data i.e UserForm
        System.out.println("User Form Data: " + userForm);

        // class
        User user = User.builder()
                .name(userForm.getName())
                .email(userForm.getEmail())
                .password(userForm.getPassword())
                .about(userForm.getAbout())
                .phoneNumber(userForm.getContact())
                .emailVerified(false)
                .phoneVerified(false)
                .provider(com.scm.entity.Providers.SELF)
                .build();
        
          // validate the form data
        // save the data to the database
        userService.saveUser(user);
        // message success
        Message successMessage = Message
                .builder()
                .content("Registration successful! Please log in.")
                .messageType(com.scm.helper.MessageType.GREEN)
                .build();
        session.setAttribute("successMessage", successMessage);
        // redirect to register page
        return "redirect:/register"; // Redirect to register page with success message

    }

}
