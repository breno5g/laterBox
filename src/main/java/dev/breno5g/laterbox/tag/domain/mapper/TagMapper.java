package dev.breno5g.laterbox.tag.domain.mapper;

import dev.breno5g.laterbox.tag.application.dto.CreateTagDTO;
import dev.breno5g.laterbox.tag.application.dto.TagResponseDTO;
import dev.breno5g.laterbox.tag.domain.entity.Tag;
import dev.breno5g.laterbox.user.domain.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TagMapper {
    public static TagResponseDTO map(Tag tag) {
        return TagResponseDTO
                .builder()
                .id(tag.getId())
                .name(tag.getName())
                .color(tag.getColor())
                .build();
    }

    public static Tag map(CreateTagDTO createTagDTO) {
        return Tag.builder()
                .name(createTagDTO.name())
                .color(createTagDTO.color())
                .user(User.builder().id(createTagDTO.userId()).build())
                .build();
    }
}
