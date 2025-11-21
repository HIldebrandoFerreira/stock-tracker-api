package com.brando.stocktracker.controller;

import com.brando.stocktracker.controller.request.RegisterUserRequest;
import com.brando.stocktracker.entity.Role;
import com.brando.stocktracker.entity.User;
import com.brando.stocktracker.exception.ResourceAlreadyExistsException;
import com.brando.stocktracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock/register")
@RequiredArgsConstructor
public class RegisterController {

    private final UserRepository repository;

    @PostMapping
    public ResponseEntity<Void> register(@RequestHeader(value = "isAdmin", required = false) boolean isAdmin,
                                         @RequestBody RegisterUserRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email já cadastrado, email: " + request.getEmail());
        }

        User user = User.builder()
                .name(request.getNome())
                .email(request.getEmail())
                .roles(isAdmin ? List.of(Role.ADMIN, Role.USER) : List.of(Role.USER))
                .password(new BCryptPasswordEncoder().encode(request.getSenha()))
                .build();

        repository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}