package dev.breno5g.laterbox.config;

import lombok.Builder;

@Builder
public record JWTUserData(Long userId) {
}
