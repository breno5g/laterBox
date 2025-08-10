package dev.breno5g.laterbox.link.controller;

import dev.breno5g.laterbox.link.application.dto.CreateLinkDTO;
import dev.breno5g.laterbox.link.application.dto.ResponseLinkDTO;
import dev.breno5g.laterbox.link.application.exceptions.LinkAlreadyExistsException;
import dev.breno5g.laterbox.link.application.service.LinkService;
import dev.breno5g.laterbox.link.domain.entity.Link;
import dev.breno5g.laterbox.link.domain.mapper.LinkMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinkController {
    private final LinkService linkService;

    @PostMapping
    public ResponseEntity<ResponseLinkDTO> create(@Valid @RequestBody CreateLinkDTO createLinkDTO) throws LinkAlreadyExistsException {
        final Link link = this.linkService.create(createLinkDTO);
        return ResponseEntity.ok(LinkMapper.map(link));
    }
}
