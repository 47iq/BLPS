package org.iq47.controller;

import org.iq47.model.entity.User;
import org.iq47.network.request.UserRequest;
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

   /* private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService){
        this.authenticationManager=authenticationManager;
        this.authService = authService;
    }*/

    @PostMapping("")
    public ResponseEntity register(@RequestBody UserRequest request){
        try{
            String username = request.getUsername();
            if(authService.loadUserByUsername(username)!=null){
                throw new NonUniqueResultException("User with such username has been already registered");
            }
            authService.saveUser(new User(username, request.getPassword()));

            return ResponseEntity.ok().build();
        } catch (NonUniqueResultException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("sosi hui");
        }
    }
}