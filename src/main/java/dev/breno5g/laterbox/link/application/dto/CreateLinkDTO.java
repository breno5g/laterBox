package dev.breno5g.laterbox.link.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateLinkDTO(
        @NotNull String url,
        @NotNull String title,
        @NotNull String description,
        @NotNull UUID user_id
) {
}
