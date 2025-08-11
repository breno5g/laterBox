package dev.breno5g.laterbox.link.controller.Interface;

import dev.breno5g.laterbox.link.application.dto.CreateLinkDTO;
import dev.breno5g.laterbox.link.application.dto.ResponseLinkDTO;
import dev.breno5g.laterbox.link.application.exceptions.LinkAlreadyExistsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@Tag(name = "Links", description = "Links endpoints")
public interface ILinkController {

    @Operation(summary = "Create a new link", description = "Creates a new link for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Link created successfully",
                    content = @Content(schema = @Schema(implementation = ResponseLinkDTO.class))),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
            @ApiResponse(responseCode = "409", description = "Link already exists", content = @Content)
    })
    ResponseEntity<ResponseLinkDTO> create(CreateLinkDTO createLinkDTO) throws LinkAlreadyExistsException;

    @Operation(summary = "Get all links", description = "Returns all links for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Links found successfully",
                    content = @Content(schema = @Schema(implementation = ResponseLinkDTO[].class)))
    })
    ResponseEntity<List<ResponseLinkDTO>> findAll();

    @Operation(summary = "Delete a link", description = "Deletes a link for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Link deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Link not found", content = @Content)
    })
    ResponseEntity<String> deleteById(UUID id);
}
