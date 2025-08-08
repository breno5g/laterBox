package dev.breno5g.laterbox.link.application.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateLinkDTO(
        @NotNull String url,
        @NotNull String title,
        @NotNull String description,
        @NotNull UUID user_id
) {
}
