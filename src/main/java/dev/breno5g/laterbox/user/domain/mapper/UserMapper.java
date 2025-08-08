package dev.breno5g.laterbox.user.domain.mapper;

import dev.breno5g.laterbox.user.application.dto.CreateUserDTO;
import dev.breno5g.laterbox.user.domain.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public static User map(CreateUserDTO createUserDTO) {
        return User
                .builder()
                .username(createUserDTO.username())
                .password(createUserDTO.password())
                .build();
    }
}
