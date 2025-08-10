package dev.breno5g.laterbox.link.domain.mapper;

import dev.breno5g.laterbox.link.application.dto.CreateLinkDTO;
import dev.breno5g.laterbox.link.application.dto.ResponseLinkDTO;
import dev.breno5g.laterbox.link.domain.entity.Link;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LinkMapper {
    public static Link map(CreateLinkDTO createLinkDTO) {
        return Link
                .builder()
                .title(createLinkDTO.title())
                .description(createLinkDTO.description())
                .url(createLinkDTO.url())
                .userId(createLinkDTO.userId())
                .build();
    }

    public static ResponseLinkDTO map(Link link) {
        return ResponseLinkDTO
                .builder()
                .id(link.getId())
                .url(link.getUrl())
                .title(link.getTitle())
                .description(link.getDescription())
                .createdAt(link.getCreatedAt())
                .isRead(link.getIsRead())
                .readAt(link.getReadAt())
                .isFavorite(link.getIsFavorite())
                .userId(link.getUserId())
                .build();
    }
}
