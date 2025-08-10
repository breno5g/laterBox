package dev.breno5g.laterbox.link.application.service;

import dev.breno5g.laterbox.link.application.dto.CreateLinkDTO;
import dev.breno5g.laterbox.link.application.exceptions.LinkAlreadyExistsException;
import dev.breno5g.laterbox.link.application.exceptions.LinkExceptions;
import dev.breno5g.laterbox.link.application.service.Interface.ILinkInterface;
import dev.breno5g.laterbox.link.domain.entity.Link;
import dev.breno5g.laterbox.link.domain.mapper.LinkMapper;
import dev.breno5g.laterbox.link.domain.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LinkService implements ILinkInterface {
    private final LinkRepository linkRepository;

    public Link create(CreateLinkDTO createLinkDTO) throws LinkAlreadyExistsException {
        final Optional<Link> optLink = this.linkRepository.findByUrl(createLinkDTO.url());
        if (optLink.isPresent()) {
            throw LinkExceptions.LINK_ALREADY_EXISTS_EXCEPTION;
        }

        final Link link = LinkMapper.map(createLinkDTO);
        this.linkRepository.save(link);
        return link;
    }
}
