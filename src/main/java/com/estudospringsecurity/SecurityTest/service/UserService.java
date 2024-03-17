package com.estudospringsecurity.SecurityTest.service;

import com.estudospringsecurity.SecurityTest.dto.RegisterRequest;
import com.estudospringsecurity.SecurityTest.entity.Role;
import com.estudospringsecurity.SecurityTest.entity.User;
import com.estudospringsecurity.SecurityTest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passEnconder;

    public List<User> getAll(){
        return repository.findAll();
    }

    public User registerNewUser(RegisterRequest request) {

        if (repository.existsByEmail(request.email())) {
            throw new IllegalArgumentException();
        }

        return repository.save(
                User.builder()
                        .username(request.username())
                        .email(request.email())
                        .role(Role.USER)
                        .password(passEnconder.encode(request.password())).build()
        );

    }

}
