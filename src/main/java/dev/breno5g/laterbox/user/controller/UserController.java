package dev.breno5g.laterbox.user.controller;

import dev.breno5g.laterbox.user.application.dto.CreateUserDTO;
import dev.breno5g.laterbox.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @RequestMapping("/register")
    public ResponseEntity<String> register(@RequestBody CreateUserDTO createUserDTO) {
        this.userService.createUser(createUserDTO);
        return ResponseEntity.ok("User created successfully");
    }
}
