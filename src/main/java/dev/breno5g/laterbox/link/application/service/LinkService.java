package dev.breno5g.laterbox.link.application.service;

import dev.breno5g.laterbox.link.application.dto.CreateLinkDTO;
import dev.breno5g.laterbox.link.application.dto.ResponseLinkDTO;
import dev.breno5g.laterbox.link.application.exceptions.LinkAlreadyExistsException;
import dev.breno5g.laterbox.link.application.exceptions.LinkExceptions;
import dev.breno5g.laterbox.link.application.exceptions.LinkNotFoundException;
import dev.breno5g.laterbox.link.application.service.Interface.ILinkInterface;
import dev.breno5g.laterbox.link.domain.entity.Link;
import dev.breno5g.laterbox.link.domain.mapper.LinkMapper;
import dev.breno5g.laterbox.link.domain.repository.LinkRepository;
import dev.breno5g.laterbox.tag.domain.entity.Tag;
import dev.breno5g.laterbox.tag.domain.mapper.TagMapper;
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
        if (linkRepository.existsByUrlAndUserId(createLinkDTO.url(), createLinkDTO.userId()))
            throw LinkExceptions.LINK_ALREADY_EXISTS_EXCEPTION;

        List<Tag> tags = createLinkDTO.tags().stream()
                .map(TagMapper::map)
                .map(tag -> tagRepository.findByNameAndUserId(tag.getName(), createLinkDTO.userId())
                        .orElseGet(() -> {
                            tag.setUser(User.builder().id(createLinkDTO.userId()).build());
                            return tagRepository.save(tag);
                        }))
                .toList();

        Link link = LinkMapper.map(createLinkDTO);
        link.setTags(tags);
        return linkRepository.save(link);
    }

    public List<ResponseLinkDTO> findAll(UUID userId) {
        List<Link> links = this.linkRepository.findAllByUserId(userId);

        return links.stream().map(LinkMapper::map).toList();
    }

    public Link findById(UUID id) {
        return null;
    }

    @Transactional
    public void deleteById(UUID id, UUID userId) throws LinkNotFoundException {
        Integer deletedLink =this.linkRepository.deleteByIdAndUserId(id, userId);
        if (deletedLink == 0) throw LinkExceptions.LINK_NOT_FOUND_EXCEPTION;
    }
}
