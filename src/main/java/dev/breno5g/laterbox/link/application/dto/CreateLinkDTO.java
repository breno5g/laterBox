package dev.breno5g.laterbox.link.application.dto;

import dev.breno5g.laterbox.tag.application.dto.CreateTagDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CreateLinkDTO(
        @NotEmpty(message = "Link url must not be empty")
        @Size(max = 2048, message = "Link url must not exceed 2048 characters")
        @Pattern(
            regexp = "^(https?://).+",
            message = "Only http/https URLs are allowed"
        )
        String url,

        @NotEmpty(message = "Link title must not be empty")
        @Size(max = 256, message = "Title too long")
        String title,

        @NotEmpty(message = "Link description must not be empty")
        @Size(max = 4096, message = "Description too long")
        String description,

        List<CreateTagDTO> tags,
        UUID userId
) {

}
