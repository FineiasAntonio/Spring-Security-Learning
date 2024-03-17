package com.estudospringsecurity.SecurityTest.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.estudospringsecurity.SecurityTest.dto.LoginRequest;
import com.estudospringsecurity.SecurityTest.entity.User;
import com.estudospringsecurity.SecurityTest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    Algorithm algorithm = Algorithm.HMAC256("biscoito");

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    // TODO: exception catching
    public String generateToken(LoginRequest request) {
        User user = userRepository.findByEmail(request.email());

        return JWT.create()
                .withIssuer("API-AUTH")
                .withSubject(user.getEmail())
                .withExpiresAt(
                        LocalDateTime.now().plusHours(6).toInstant(ZoneOffset.UTC)
                ).sign(algorithm);
    }

    public String validateToken(String token) {
        return JWT.require(algorithm)
                .withIssuer("API-AUTH")
                .build()
                .verify(token)
                .getSubject();
    }

}
