package com.brando.stocktracker.controller;

import com.brando.stocktracker.controller.request.AuthRequest;
import com.brando.stocktracker.controller.response.AuthResponse;
import com.brando.stocktracker.entity.User;
import com.brando.stocktracker.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        log.info("dados: " + userAndPass);
        log.info("valiar autenticação");
        Authentication authentication = authenticationManager.authenticate(userAndPass);
        log.info("Pegar usuário");
        User user = (User) authentication.getPrincipal();
        log.info("Gerar token");
        String token = tokenService.generateToken(user);

        return ResponseEntity.ok(AuthResponse.builder()
                        .accessToken(token)
                        .nome(user.getName())
                        .build());
    }
}