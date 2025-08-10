package dev.breno5g.laterbox.link.application.service.Interface;

import dev.breno5g.laterbox.link.application.dto.CreateLinkDTO;
import dev.breno5g.laterbox.link.application.exceptions.LinkAlreadyExistsException;
import dev.breno5g.laterbox.link.domain.entity.Link;

public interface ILinkInterface {
    Link create(CreateLinkDTO createLinkDTO) throws LinkAlreadyExistsException;
}
