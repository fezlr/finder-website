//package com.bsuir_finder.controller;
//
//import com.bsuir_finder.entity.UserEntity;
//import com.bsuir_finder.service.CustomUserDetailsService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//@RequestMapping("register")
//public class AuthenticationController {
//
//    private final CustomUserDetailsService userDetailsService;
//
//    public AuthenticationController(CustomUserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
////    @GetMapping
////    public String register(
////            Model model
////    ) {
////        UserEntity user = new UserEntity();
////        model.addAttribute("user", user);
////        return "register";
////    }
//
//    @PostMapping
//    public String register(
//            @ModelAttribute("user") UserEntity user,
//            Model model,
//            RedirectAttributes redirectAttributes
//    ) {
//        userDetailsService.registerUser(user);
//        redirectAttributes.addFlashAttribute(
//                "message",
//                "Please confirm your email address"
//
//        );
//        return "redirect:/register";
//    }
//
//    @GetMapping("/confirmToken")
//    public String confirmToken(
//            @RequestParam("token") String token,
//            Model model
//    ) {
//        userDetailsService.confirmToken(token);
//        return "confirmToken";
//    }
//}