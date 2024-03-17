package com.estudospringsecurity.SecurityTest.controller;

import com.estudospringsecurity.SecurityTest.dto.LoginRequest;
import com.estudospringsecurity.SecurityTest.dto.RegisterRequest;
import com.estudospringsecurity.SecurityTest.entity.User;
import com.estudospringsecurity.SecurityTest.service.AuthService;
import com.estudospringsecurity.SecurityTest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerNewUser(request));
    }

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody LoginRequest request){

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.status(HttpStatus.OK).body(authService.generateToken(request));
    }

}
