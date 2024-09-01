package org.example.finzo_auth_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.finzo_auth_service.entity.Roles;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo {

    UUID uuid;
    String email;
    Roles role;
}
