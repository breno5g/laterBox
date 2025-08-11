package dev.breno5g.laterbox.link.controller;

import dev.breno5g.laterbox.config.JWTUserData;
import dev.breno5g.laterbox.link.application.dto.CreateLinkDTO;
import dev.breno5g.laterbox.link.application.dto.ResponseLinkDTO;
import dev.breno5g.laterbox.link.application.exceptions.LinkAlreadyExistsException;
import dev.breno5g.laterbox.link.application.service.LinkService;
import dev.breno5g.laterbox.link.controller.Interface.ILinkController;
import dev.breno5g.laterbox.link.domain.entity.Link;
import dev.breno5g.laterbox.link.domain.mapper.LinkMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinkController implements ILinkController {
    private final LinkService linkService;

    @Override
    @PostMapping
    public ResponseEntity<ResponseLinkDTO> create(@Valid @RequestBody CreateLinkDTO createLinkDTO) throws LinkAlreadyExistsException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        JWTUserData userData = (JWTUserData) auth.getPrincipal();
        CreateLinkDTO linkDTO = new CreateLinkDTO(
                createLinkDTO.url(),
                createLinkDTO.title(),
                createLinkDTO.description(),
                createLinkDTO.tags(),
                userData.userId()
        );
        final Link link = this.linkService.create(linkDTO);
        return ResponseEntity.ok(LinkMapper.map(link));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ResponseLinkDTO>> findAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        JWTUserData userData = (JWTUserData) auth.getPrincipal();

        final List<ResponseLinkDTO> links = this.linkService.findAll(userData.userId());
        return ResponseEntity.ok(links);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") UUID id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        JWTUserData userData = (JWTUserData) auth.getPrincipal();

        this.linkService.deleteById(id, userData.userId());
        return ResponseEntity.ok("Link deleted successfully");
    }
}
