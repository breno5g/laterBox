package dev.breno5g.laterbox.user.controller;

import dev.breno5g.laterbox.user.application.dto.CreateUserDTO;
import dev.breno5g.laterbox.user.application.exceptions.InvalidUsernameOrPasswordException;
import dev.breno5g.laterbox.user.application.exceptions.UserAlreadyExistsException;
import dev.breno5g.laterbox.user.application.exceptions.UserExceptions;
import dev.breno5g.laterbox.user.application.service.UserService;
import dev.breno5g.laterbox.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @RequestMapping("/register")
    public ResponseEntity<String> register(@RequestBody CreateUserDTO createUserDTO) throws UserAlreadyExistsException {
        this.userService.createUser(createUserDTO);
        return ResponseEntity.ok("User created successfully");
    }

    @RequestMapping("/login")
    public ResponseEntity<String> login(@RequestBody CreateUserDTO request) throws InvalidUsernameOrPasswordException {
        try {
            UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.username(), request.password());
            Authentication authenticate = authenticationManager.authenticate(userAndPass);

            User user = (User) authenticate.getPrincipal();
//          String token = tokenService.generateToken(user);
            String token = "tokenService.generateToken(user)";

            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e) {
            throw UserExceptions.INVALID_USERNAME_OR_PASSWORD_EXCEPTION;
        }
    }
}
