package dev.breno5g.laterbox.tag.application.service.Interface;

import dev.breno5g.laterbox.tag.application.dto.CreateTagDTO;
import dev.breno5g.laterbox.tag.application.exceptions.TagAlreadyExistsException;
import dev.breno5g.laterbox.tag.domain.entity.Tag;

public interface ITagService {
    Tag create(CreateTagDTO createTagDTO) throws TagAlreadyExistsException;
}
