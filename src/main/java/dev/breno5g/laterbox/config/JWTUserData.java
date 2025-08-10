package dev.breno5g.laterbox.config;

import lombok.Builder;

import java.util.UUID;

@Builder
public record JWTUserData(UUID userId, String username) {
}
