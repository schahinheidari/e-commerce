package org.example.shoppingbackend.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
}
