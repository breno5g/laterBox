package dev.breno5g.laterbox.user.application.dto;

import jakarta.validation.constraints.NotEmpty;

public record CreateUserDTO(
        @NotEmpty(message = "User name is requested") String username,
        @NotEmpty(message = "User password is requested") String password
        ) {
}
