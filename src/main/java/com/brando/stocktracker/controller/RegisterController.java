package com.brando.stocktracker.controller;

import com.brando.stocktracker.controller.request.RegisterUserRequest;
import com.brando.stocktracker.entity.Role;
import com.brando.stocktracker.entity.User;
import com.brando.stocktracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock/register")
@RequiredArgsConstructor
@Log4j2
public class RegisterController {

    private  final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Void> register(@RequestHeader(value = "isAdmin", required = false) boolean isAdmin,
                                         @RequestBody RegisterUserRequest request) {

        log.info("Chamou rota para cadastrar cliente");
        User user = User.builder()
                .name(request.getNome())
                .email(request.getEmail())
                .roles(isAdmin ? List.of(Role.ADMIN, Role.USER) : List.of(Role.USER))
                .password(new BCryptPasswordEncoder().encode(request.getSenha()))
                .build();

        log.info("Savando o usuário: " +user);
        userRepository.save(user);
        log.info("mensagem de retorno");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
