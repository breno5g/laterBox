package dev.breno5g.laterbox.user.controller;

import dev.breno5g.laterbox.config.TokenService;
import dev.breno5g.laterbox.user.application.dto.CreateUserDTO;
import dev.breno5g.laterbox.user.application.dto.ResponseUserDTO;
import dev.breno5g.laterbox.user.application.exceptions.InvalidUsernameOrPasswordException;
import dev.breno5g.laterbox.user.application.exceptions.UserAlreadyExistsException;
import dev.breno5g.laterbox.user.application.service.UserService;
import dev.breno5g.laterbox.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CreateUserDTO createUserDTO) throws UserAlreadyExistsException {
        this.userService.createUser(createUserDTO);
        return ResponseEntity.ok("User created successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseUserDTO> login(@RequestBody CreateUserDTO request) {
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        Authentication authenticate = authenticationManager.authenticate(userAndPass);

        User user = (User) authenticate.getPrincipal();

        String token = this.tokenService.generateToken(user);

        return ResponseEntity.ok(new ResponseUserDTO(token));
    }
}
