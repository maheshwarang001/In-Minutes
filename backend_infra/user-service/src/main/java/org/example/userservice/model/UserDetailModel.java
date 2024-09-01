package org.example.userservice.model;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserDetailModel {
    private String first_name;
    private String last_name;
    private String phone_number;
}
