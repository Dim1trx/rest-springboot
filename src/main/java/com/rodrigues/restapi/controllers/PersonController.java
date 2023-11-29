package com.rodrigues.restapi.controllers;

import com.rodrigues.restapi.util.MediaType;
import com.rodrigues.restapi.data.vo.v1.PersonVOV1;
import com.rodrigues.restapi.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*@CrossOrigin // Allows requests from other origins*/
@RestController
@RequestMapping("api/person/v1")
@Tag(name = "People", description = "Endpoint for people's management")
public class PersonController {

    @Autowired
    private PersonService service;


    /*@CrossOrigin(origins = "http://localhost:8080")*/
    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLCICATION_YAML}
    )
    @Operation(
            summary = "Find a specific person by id", description = "Find a specific person by id",
            tags = {"People"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success: people found",
                            content = @Content(schema = @Schema(implementation = PersonVOV1.class))
                    ),
                    @ApiResponse(responseCode = "204", description = "No content", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal error", content = @Content),
            }
    )
    public PersonVOV1 findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLCICATION_YAML})
    @Operation(
            summary = "Finds all people recorded", description = "Finds all people recorded in database",
            tags = {"People"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success: people found",
                            content = {
                                    @Content(mediaType = "application/json",

                                            array = @ArraySchema(schema = @Schema(implementation = PersonVOV1.class))
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal error", content = @Content),
            }
    )
    public ResponseEntity<List<PersonVOV1>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLCICATION_YAML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLCICATION_YAML}
    )
    @Operation(
            summary = "Updates a person.", description = "Updates a person by passing in a JSON, XML or YAML representation.",
            tags = {"People"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success: person updated",
                            content = @Content(schema = @Schema(implementation = PersonVOV1.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal error", content = @Content),
            }
    )
    public PersonVOV1 update(@RequestBody PersonVOV1 person) {
        return service.update(person);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLCICATION_YAML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLCICATION_YAML}
    )
    @Operation(
            summary = "Adds a new person.", description = "Adds a new person by passing in a JSON, XML or YAML representation.",
            tags = {"People"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success: person created",
                            content = @Content(schema = @Schema(implementation = PersonVOV1.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal error", content = @Content),
            }
    )
 /*   @CrossOrigin(origins = {"http://localhost:8080", "https://rodrigues.com.br"})*/
    public PersonVOV1 create(@RequestBody PersonVOV1 person) {
        return service.create(person);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Deletes a person.", description = "Deletes a person by passing its id.",
            tags = {"People"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content", content = @Content),
                    @ApiResponse(responseCode = "404", description = "No content", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal error", content = @Content),
            }
    )
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
