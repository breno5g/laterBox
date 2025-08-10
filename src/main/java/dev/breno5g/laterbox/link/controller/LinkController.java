package dev.breno5g.laterbox.link.controller;

import dev.breno5g.laterbox.link.application.dto.CreateLinkDTO;
import dev.breno5g.laterbox.link.application.dto.ResponseLinkDTO;
import dev.breno5g.laterbox.link.domain.entity.Link;
import dev.breno5g.laterbox.link.domain.mapper.LinkMapper;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinkController {

    @PostMapping
    public ResponseEntity<Link> create(@Valid @RequestBody CreateLinkDTO createLinkDTO) {
        Link responseLinkDTO = LinkMapper.map(createLinkDTO);

        return ResponseEntity.ok(responseLinkDTO);
    }
}
