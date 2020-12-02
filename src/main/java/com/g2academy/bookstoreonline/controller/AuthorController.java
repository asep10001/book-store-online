package com.g2academy.bookstoreonline.controller;


import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.g2academy.bookstoreonline.model.Author;
import com.g2academy.bookstoreonline.service.AuthorService;
import com.g2academy.bookstoreonline.service.dto.AuthorDTO;
import com.g2academy.bookstoreonline.service.dto.BookDTO;
import com.g2academy.bookstoreonline.service.mapper.AuthorMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDTO>>  getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorDTO> findAuthorById(@PathVariable Long id) {
        return authorService.findById(id);
    }

    @GetMapping("/authors/author")
    public ResponseEntity<AuthorDTO> findAllByNameContains(@RequestParam(value = "name") String name) {
        return authorService.findAllByNameContains(name);
    }

    //
    @PutMapping("/authors/{id}/author")
    public ResponseEntity<AuthorDTO> updateAnAuthorById(@Valid @RequestBody AuthorDTO authorDto, @PathVariable(value = "id") Long id) {
        return authorService.updateAuthor(authorDto, id);
    }

    @DeleteMapping("authors/{id}")
    public void deleteAuthorById(@PathVariable Long id) {
        authorService.delete(id);
    }

    @PostMapping("/authors")
    public ResponseEntity<AuthorDTO> saveAuthor(
            @Valid @RequestBody Author request, @RequestParam(value = "publisher_id") Long publisherId
    ) {
        return authorService.saveAuthor(request, publisherId);
    }

    @PostMapping("/authors/add_book/{authorId}")
    public ResponseEntity<AuthorDTO> addBok(
            @Valid @RequestBody BookDTO request, @PathVariable(value = "authorId") Long id,
            @RequestParam(value = "publisher") Long publisherId
    ) {
        return authorService.addBook(request, id, publisherId);
    }


    @PutMapping("/authors/update_book/{authorId}")
    public ResponseEntity<AuthorDTO> updateBookById(@Valid @RequestBody BookDTO bookDTO,
                                           @PathVariable(value = "authorId") Long id,
                                           @RequestParam(value = "book") Long bookId) {
        return authorService.updateBook(bookDTO, id, bookId);
    }

    @DeleteMapping("authors/delete_book/")
    public ResponseEntity<AuthorDTO> deleteBook(
            @RequestParam(value = "author") Long id,
            @RequestParam(value = "book") Long bookId) {
        return authorService.deleteBook(id, bookId);
    }


}
