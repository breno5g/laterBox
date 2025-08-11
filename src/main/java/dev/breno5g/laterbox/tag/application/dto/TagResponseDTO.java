package dev.breno5g.laterbox.tag.application.dto;

import java.util.UUID;

public record TagResponseDTO(
        UUID id,
        String name,
        String color
) {}
