package dev.breno5g.laterbox.link.application.dto;

import dev.breno5g.laterbox.tag.domain.entity.Tag;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record CreateLinkDTO(
        @NotEmpty(message = "Link url must not be empty") String url,
        @NotEmpty(message = "Link title must not be empty") String title,
        @NotEmpty(message = "Link description must not be empty") String description,
        List<Tag> tags,
        UUID userId
) {
}
