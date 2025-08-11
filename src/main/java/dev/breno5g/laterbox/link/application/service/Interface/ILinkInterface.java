package dev.breno5g.laterbox.link.application.service.Interface;

import dev.breno5g.laterbox.link.application.dto.CreateLinkDTO;
import dev.breno5g.laterbox.link.application.dto.ResponseLinkDTO;
import dev.breno5g.laterbox.link.application.exceptions.LinkAlreadyExistsException;
import dev.breno5g.laterbox.link.domain.entity.Link;

import java.util.List;
import java.util.UUID;

public interface ILinkInterface {
    Link create(CreateLinkDTO createLinkDTO) throws LinkAlreadyExistsException;
    List<ResponseLinkDTO> findAll(UUID userId);
    Link findById(String id);
    void deleteById(String id);
}
