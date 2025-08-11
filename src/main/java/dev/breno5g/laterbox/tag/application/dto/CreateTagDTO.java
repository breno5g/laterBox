package dev.breno5g.laterbox.tag.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateTagDTO(
        @NotEmpty(message = "Tag name must not be empty") @Size(max = 42) String name,
        @Pattern(regexp = "^#[0-9a-fA-F]{6}$", message = "Tag color must be a hexadecimal color") String color,
        UUID id,
        UUID userId
) {
}
