package dev.breno5g.laterbox.user.application.service.Interface;

import dev.breno5g.laterbox.user.application.dto.CreateUserDTO;
import dev.breno5g.laterbox.user.application.exceptions.UserAlreadyExistsException;

public interface IUserService {
    void createUser(CreateUserDTO createUserDTO) throws UserAlreadyExistsException;
}
