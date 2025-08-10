package dev.breno5g.laterbox.tag.application.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record CreateTagDTO(
        @NotEmpty(message = "Tag name must not be empty") String name,
        @NotEmpty(message = "Tag color must not be empty") String color,
        UUID userId
) {
}
