package dev.breno5g.laterbox.tag.controller;

import dev.breno5g.laterbox.config.JWTUserData;
import dev.breno5g.laterbox.tag.application.dto.CreateTagDTO;
import dev.breno5g.laterbox.tag.application.exceptions.TagAlreadyExistsException;
import dev.breno5g.laterbox.tag.application.service.TagService;
import dev.breno5g.laterbox.tag.controller.Interface.ITagController;
import dev.breno5g.laterbox.tag.domain.entity.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController implements ITagController {
    private final TagService tagService;

    @PostMapping
    public ResponseEntity<Tag> create(@Valid @RequestBody CreateTagDTO createTagDTO) throws TagAlreadyExistsException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JWTUserData userData = (JWTUserData) auth.getPrincipal();

        CreateTagDTO dto = new CreateTagDTO(
                createTagDTO.name(),
                createTagDTO.color(),
                userData.userId()
        );

        final Tag tag = this.tagService.create(dto);
        return ResponseEntity.ok(tag);
    }
}
