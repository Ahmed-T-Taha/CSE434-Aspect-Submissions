package com.example.Lab3.Users.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{11}$", message = "Phone number should be 11 digits")
    private String phoneNumber;

    @NotNull(message = "Balance is required")
    @PositiveOrZero(message = "Balance must be positive")
    private Double balance;
}


