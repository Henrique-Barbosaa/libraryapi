package com.henriq.libraryapi.controller;


import com.henriq.libraryapi.dto.BookRequestDTO;
import com.henriq.libraryapi.dto.BookResponseDTO;
import com.henriq.libraryapi.mappers.BookMapper;
import com.henriq.libraryapi.model.Book;
import com.henriq.libraryapi.model.BookGender;
import com.henriq.libraryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class BookController implements GenericController {
    private final BookService service;
    private final BookMapper mapper;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid BookRequestDTO bookDTO) {
        Book book = mapper.toEntity(bookDTO);
        service.save(book);
        URI location = generateHeaderLocation(book.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getDetails(@PathVariable("id") String id){
        return service
                .findById(UUID.fromString(id))
                .map(book -> {
                    BookResponseDTO dto = mapper.toDTO(book);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id){
        return service
                .findById(UUID.fromString(id))
                .map(book -> {
                    service.delete(book);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet( () -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<BookResponseDTO>> search(
            @RequestParam(value = "title", required = false)
            String title,
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "gender", required = false)
            BookGender gender,
            @RequestParam(value = "publication-year", required = false)
            Integer publicationYear,
            @RequestParam(value = "author-name", required = false)
            String authorName,
            @RequestParam(value = "page", defaultValue = "0")
            Integer page,
            @RequestParam(value = "page-size", defaultValue = "10")
            Integer pageSize
    ){
        Page<Book> result = service.search(
                isbn, title, authorName, gender, publicationYear, page, pageSize);

        Page<BookResponseDTO> pages = result.map(mapper::toDTO);

        return ResponseEntity.ok(pages);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable("id") String id,
            @RequestBody @Valid BookRequestDTO dto){
        return service
                .findById(UUID.fromString(id))
                .map(book -> {
                    Book entity = mapper.toEntity(dto);
                    book.setPublicationDate(entity.getPublicationDate());
                    book.setIsbn(entity.getIsbn());
                    book.setPrice(entity.getPrice());
                    book.setTitle(entity.getTitle());
                    book.setGender(entity.getGender());
                    book.setAuthor(entity.getAuthor());

                    service.save(book);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }
}
