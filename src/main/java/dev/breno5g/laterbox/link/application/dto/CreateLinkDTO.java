package dev.breno5g.laterbox.link.application.dto;

import jakarta.validation.constraints.NotEmpty;

public record CreateLinkDTO(
        @NotEmpty(message = "Link url must not be empty") String url,
        @NotEmpty(message = "Link title must not be empty") String title,
        @NotEmpty(message = "Link description must not be empty") String description,
        java.util.UUID userId
) {
}
