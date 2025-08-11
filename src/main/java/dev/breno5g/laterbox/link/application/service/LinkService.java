package dev.breno5g.laterbox.link.application.service;

import dev.breno5g.laterbox.link.application.dto.CreateLinkDTO;
import dev.breno5g.laterbox.link.application.exceptions.LinkAlreadyExistsException;
import dev.breno5g.laterbox.link.application.exceptions.LinkExceptions;
import dev.breno5g.laterbox.link.application.service.Interface.ILinkInterface;
import dev.breno5g.laterbox.link.domain.entity.Link;
import dev.breno5g.laterbox.link.domain.mapper.LinkMapper;
import dev.breno5g.laterbox.link.domain.repository.LinkRepository;
import dev.breno5g.laterbox.tag.domain.repository.TagRepository;
import dev.breno5g.laterbox.user.domain.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkService implements ILinkInterface {
    private final LinkRepository linkRepository;
    private final TagRepository tagRepository;

    @Transactional
    public Link create(CreateLinkDTO createLinkDTO) throws LinkAlreadyExistsException {
        if (linkRepository.findByUrl(createLinkDTO.url()).isPresent()) {
            throw LinkExceptions.LINK_ALREADY_EXISTS_EXCEPTION;
        };

        createLinkDTO.tags().forEach(tag ->
                tagRepository.findByNameAndUserId(tag.getName(), createLinkDTO.userId())
                        .orElseGet(() -> {
                            tag.setUser(User.builder().id(createLinkDTO.userId()).build());
                            return tagRepository.save(tag);
                        })
        );

        return linkRepository.save(LinkMapper.map(createLinkDTO));
    }
}
