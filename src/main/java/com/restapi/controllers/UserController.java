package com.restapi.controllers;

import com.restapi.models.UserModel;
import com.restapi.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping
    public ResponseEntity<List<UserModel>> findAll(){
        List<UserModel> users = customUserDetailsService.findAll();
        return new ResponseEntity<List<UserModel>>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity register(@RequestBody UserModel user){
        customUserDetailsService.register(user);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody UserModel user){
        customUserDetailsService.update(user);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody String username){
        customUserDetailsService.delete(username);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
