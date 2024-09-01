package org.example.finzo_auth_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCredential {

    String email;
    String pin;


}
