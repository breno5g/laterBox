package dev.breno5g.laterbox.link.application.dto;

import dev.breno5g.laterbox.tag.domain.entity.Tag;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record  ResponseLinkDTO(
        UUID id,
        String title,
        String url,
        String description,
        Boolean isRead,
        Boolean isFavorite,
        LocalDateTime createdAt,
        LocalDateTime readAt,
        List<Tag> tags,
        UUID userId
) {
}
