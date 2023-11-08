package com.rodrigues.restapi.controllers;

import com.rodrigues.restapi.data.vo.v1.BookVOV1;
import com.rodrigues.restapi.data.vo.v1.PersonVOV1;
import com.rodrigues.restapi.services.BookService;
import com.rodrigues.restapi.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/book/v1")
@Tag(name = "Book.", description = "Book Endpoint.")
public class BookController {
    @Autowired
    private BookService service;

    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLCICATION_YAML}
    )
    @Operation(
            summary = "Find a specific book", description = "Find a specific book by id",
            tags = {"Book"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success: people found",
                            content = @Content(schema = @Schema(implementation = BookVOV1.class))
                    ),
                    @ApiResponse(responseCode = "204", description = "No content", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal error", content = @Content),
            }
    )
    public BookVOV1 findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLCICATION_YAML})
    @Operation(
            summary = "Find all books recorded", description = "Find all books recorded in database",
            tags = {"Book"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success: books found",
                            content = {
                                    @Content(mediaType = "application/json",

                                            array = @ArraySchema(schema = @Schema(implementation = BookVOV1.class))
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal error", content = @Content),
            }
    )
    public ResponseEntity<List<BookVOV1>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLCICATION_YAML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLCICATION_YAML}
    )
    @Operation(
            summary = "Adds a new book.", description = "Adds a new book by passing in a JSON, XML or YAML representation.",
            tags = {"Book"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success: book created",
                            content = @Content(schema = @Schema(implementation = BookVOV1.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal error", content = @Content),
            }
    )
    public ResponseEntity<BookVOV1> create(@RequestBody BookVOV1 bookVOV1) {
        return ResponseEntity.ok().body(service.create(bookVOV1));
    }
    @PutMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLCICATION_YAML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLCICATION_YAML}
    )
    @Operation(
            summary = "Updates a book.", description = "Updates a book by passing in a JSON, XML or YAML representation.",
            tags = {"Book"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success: book updated",
                            content = @Content(schema = @Schema(implementation = PersonVOV1.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal error", content = @Content),
            }
    )
    public ResponseEntity<BookVOV1> update(@RequestBody BookVOV1 bookVOV1) {
        return ResponseEntity.ok().body(service.update(bookVOV1));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Deletes a book.", description = "Deletes a book by passing its id.",
            tags = {"Book"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content", content = @Content),
                    @ApiResponse(responseCode = "404", description = "No content", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal error", content = @Content),
            }
    )
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
