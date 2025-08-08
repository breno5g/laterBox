package dev.breno5g.laterbox.link.application.dto;

import dev.breno5g.laterbox.user.domain.entity.User;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record  ResponseLinkDTO(
        UUID id,
        String title,
        String url,
        Boolean isRead,
        Boolean isFavorite,
        LocalDateTime createdAt,
        LocalDateTime readAt,
        User userId
) {
}
