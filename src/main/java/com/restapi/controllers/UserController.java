package com.restapi.controllers;

import com.restapi.models.ApplicationUser;
import com.restapi.models.UserModel;
import com.restapi.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping
    public List<UserModel> findAll(){
        return customUserDetailsService.findAll();
    }

    @PostMapping
    public void register(@RequestBody UserModel user){
         customUserDetailsService.register(user);
    }

}
