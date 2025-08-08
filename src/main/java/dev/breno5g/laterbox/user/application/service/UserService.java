package dev.breno5g.laterbox.user.application.service;

import dev.breno5g.laterbox.user.application.dto.CreateUserDTO;
import dev.breno5g.laterbox.user.application.service.Interface.IUserService;
import dev.breno5g.laterbox.user.domain.entity.User;
import dev.breno5g.laterbox.user.domain.mapper.UserMapper;
import dev.breno5g.laterbox.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Override
    public void createUser(CreateUserDTO createUserDTO) {
        final Optional<User> userOptional = this.userRepository.findByUsername(createUserDTO.username());

        if (userOptional.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        final User user = UserMapper.map(createUserDTO);
        this.userRepository.save(user);
    }
}
