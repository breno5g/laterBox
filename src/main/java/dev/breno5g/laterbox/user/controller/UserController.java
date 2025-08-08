package dev.breno5g.laterbox.user.controller;

import dev.breno5g.laterbox.user.application.dto.CreateUserDTO;
import dev.breno5g.laterbox.user.application.exceptions.UserAlreadyExistsException;
import dev.breno5g.laterbox.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @RequestMapping("/register")
    public ResponseEntity<String> register(@RequestBody CreateUserDTO createUserDTO) throws UserAlreadyExistsException {
        this.userService.createUser(createUserDTO);
        return ResponseEntity.ok("User created successfully");
    }
}
