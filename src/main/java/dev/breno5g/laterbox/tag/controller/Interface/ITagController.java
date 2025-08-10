package dev.breno5g.laterbox.tag.controller.Interface;

import dev.breno5g.laterbox.tag.application.dto.CreateTagDTO;
import dev.breno5g.laterbox.tag.application.exceptions.TagAlreadyExistsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Tags", description = "Tags endpoints")
public interface ITagController {

    @Operation(summary = "Create a new tag", description = "Creates a new tag for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag created successfully",
                    content = @Content(schema = @Schema(implementation = dev.breno5g.laterbox.tag.domain.entity.Tag.class))),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
            @ApiResponse(responseCode = "409", description = "Tag already exists", content = @Content)
    })
    ResponseEntity<dev.breno5g.laterbox.tag.domain.entity.Tag> create(CreateTagDTO createTagDTO) throws TagAlreadyExistsException;
}
