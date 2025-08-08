package dev.breno5g.laterbox.user.application.service.Interface;

import dev.breno5g.laterbox.user.application.dto.CreateUserDTO;
import dev.breno5g.laterbox.user.application.exceptions.UserAlreadyExistsException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    void createUser(CreateUserDTO createUserDTO) throws UserAlreadyExistsException;
}
