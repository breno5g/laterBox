package dev.breno5g.laterbox.link.application.service;

import dev.breno5g.laterbox.link.application.dto.CreateLinkDTO;
import dev.breno5g.laterbox.link.application.dto.ResponseLinkDTO;
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

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LinkService implements ILinkInterface {
    private final LinkRepository linkRepository;
    private final TagRepository tagRepository;

    @Transactional
    public Link create(CreateLinkDTO createLinkDTO) throws LinkAlreadyExistsException {
        if (linkRepository.existsByUrl(createLinkDTO.url())) throw LinkExceptions.LINK_ALREADY_EXISTS_EXCEPTION;

        createLinkDTO.tags().forEach(tag -> {
            if (tagRepository.existsTagByNameAndUserId(tag.getName(), createLinkDTO.userId())) return;

            tag.setUser(User.builder().id(createLinkDTO.userId()).build());
            tagRepository.save(tag);
        });

        return linkRepository.save(LinkMapper.map(createLinkDTO));
    }

    public List<ResponseLinkDTO> findAll(UUID userId) {
        List<Link> links = this.linkRepository.findAllByUserId(userId);

        return links.stream().map(LinkMapper::map).toList();
    }

    public Link findById(UUID id) {
        return null;
    }

    @Transactional
    public void deleteById(UUID id, UUID userId) {
        this.linkRepository.deleteByIdAndUserId(id, userId);
    }
}
