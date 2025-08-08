package dev.breno5g.laterbox.user.application.service;

import dev.breno5g.laterbox.user.application.dto.CreateUserDTO;
import dev.breno5g.laterbox.user.application.exceptions.UserAlreadyExistsException;
import dev.breno5g.laterbox.user.application.exceptions.UserExceptions;
import dev.breno5g.laterbox.user.application.service.Interface.IUserService;
import dev.breno5g.laterbox.user.domain.entity.User;
import dev.breno5g.laterbox.user.domain.mapper.UserMapper;
import dev.breno5g.laterbox.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(CreateUserDTO createUserDTO) throws UserAlreadyExistsException {
        final Optional<User> userOptional = this.userRepository.findByUsername(createUserDTO.username());

        if (userOptional.isPresent()) {
            throw UserExceptions.USER_ALREADY_EXISTS_EXCEPTION;
        }

        final User user = UserMapper.map(createUserDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
