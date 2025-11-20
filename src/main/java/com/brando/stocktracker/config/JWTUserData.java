package com.brando.stocktracker.config;

import com.brando.stocktracker.entity.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class JWTUserData {

    private String userId;
    private String email;
    private List<Role> roles;
}