package dev.breno5g.laterbox.tag.application.service;

import dev.breno5g.laterbox.tag.application.dto.CreateTagDTO;
import dev.breno5g.laterbox.tag.application.exceptions.TagAlreadyExistsException;
import dev.breno5g.laterbox.tag.application.exceptions.TagExceptions;
import dev.breno5g.laterbox.tag.application.exceptions.TagNotFoundException;
import dev.breno5g.laterbox.tag.application.service.Interface.ITagService;
import dev.breno5g.laterbox.tag.domain.entity.Tag;
import dev.breno5g.laterbox.tag.domain.repository.TagRepository;
import dev.breno5g.laterbox.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagService implements ITagService {
    private final TagRepository tagRepository;

    @Override
    public Tag create(CreateTagDTO createTagDTO) throws TagAlreadyExistsException {
        final Optional<Tag> optTag = this.tagRepository.findByNameAndUserId(createTagDTO.name(), createTagDTO.userId());
        if (optTag.isPresent()) {
            throw TagExceptions.TAG_ALREADY_EXISTS_EXCEPTION;
        }

        final User userRef = User.builder().id(createTagDTO.userId()).build();

        final Tag tag = Tag.builder()
                .name(createTagDTO.name())
                .color(createTagDTO.color())
                .user(userRef)
                .build();

        this.tagRepository.save(tag);
        return tag;
    }

    @Override
    public Tag findByIdAndUserId(UUID id, UUID userId) throws TagNotFoundException {
        return this.tagRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> TagExceptions.TAG_NOT_FOUND_EXCEPTION);
    }
}
