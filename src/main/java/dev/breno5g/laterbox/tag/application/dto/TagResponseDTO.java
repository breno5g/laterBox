package dev.breno5g.laterbox.tag.application.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record TagResponseDTO(
        UUID id,
        String name,
        String color
) {}
