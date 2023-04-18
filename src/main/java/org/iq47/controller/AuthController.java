package org.iq47.controller;

import org.iq47.model.entity.User;
import org.iq47.network.request.UserRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NonUniqueResultException;

@RestController
@RequestMapping("/api/v1/users")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("")
    public ResponseEntity<?> register(@RequestBody UserRequest request){
        String username = request.getUsername();
        if(authService.loadUserByUsername(username)!=null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseWrapper("User with such username has been already registered"));
        }
        authService.saveUser(new User(username, request.getPassword()));

        return ResponseEntity.ok().build();
    }
}