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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LinkService implements ILinkInterface {
    private final LinkRepository linkRepository;
    private final TagRepository tagRepository;

    @Transactional
    public Link create(CreateLinkDTO createLinkDTO) throws LinkAlreadyExistsException {
        final Optional<Link> optLink = this.linkRepository.findByUrl(createLinkDTO.url());
        if (optLink.isPresent()) {
            throw LinkExceptions.LINK_ALREADY_EXISTS_EXCEPTION;
        }

        if (createLinkDTO.tags().isEmpty()) {
            final Link link = LinkMapper.map(createLinkDTO);
            return linkRepository.save(link);
        }

        createLinkDTO.tags().forEach(tag -> {
            tagRepository.findByNameAndUser_Id(tag.getName(), createLinkDTO.userId()).orElseGet(() -> {
                User user = User.builder().id(createLinkDTO.userId()).build();
                tag.setUser(user);
                return tagRepository.save(tag);
            });
        });

        final Link link = LinkMapper.map(createLinkDTO);
        this.linkRepository.save(link);
        return link;
    }
}
