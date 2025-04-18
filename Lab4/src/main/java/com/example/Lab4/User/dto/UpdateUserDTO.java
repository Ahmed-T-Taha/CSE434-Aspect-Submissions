package com.example.Lab4.User.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^[0-9]{11}$", message = "Phone number should be 11 digits")
    private String phoneNumber;

    @PositiveOrZero(message = "Balance must be positive")
    private Double balance;
}



