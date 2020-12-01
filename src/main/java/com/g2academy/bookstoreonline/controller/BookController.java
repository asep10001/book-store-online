package com.g2academy.bookstoreonline.controller;

import com.g2academy.bookstoreonline.model.Book;
import com.g2academy.bookstoreonline.repository.AuthorRepository;
import com.g2academy.bookstoreonline.service.BookService;

import com.g2academy.bookstoreonline.service.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final BookService bookService;
    private final AuthorRepository authorRepository;


    public BookController(BookService bookService, AuthorRepository authorRepository) {
        this.bookService = bookService;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/books/")
    public ResponseEntity<List<BookDTO>> getAllBooks() {

        return bookService.findAllbook();
    }

    @GetMapping("/books/search/author")
    public  ResponseEntity<List<BookDTO>> findAllBookByAuthorName(@RequestParam (value = "name") String authorName){
        return bookService.findAllBookByAuthorNameContaining(authorName);
    }

    @GetMapping("/books/search/isbn")
    public  ResponseEntity<BookDTO> findBookByIsbn(@RequestParam (value = "isbn") String isbn){
        return bookService.findAllBookByIsbn(isbn);
    }

    @GetMapping("/books/search/title")
    public  ResponseEntity<List<BookDTO>> findBookByTitle(@RequestParam (value = "title") String title){
        return bookService.findAllBookByTitle(title);
    }

    @GetMapping("books/search/publisher")
    public  ResponseEntity<List<BookDTO>> findBookByPublisher(@RequestParam (value = "publisher") String publisher){
        return bookService.findAllBookByPublisher(publisher);
    }

    @GetMapping("/books/book/{id}")
    public ResponseEntity<BookDTO> getAllBooksByAuthorId(@PathVariable(value = "id") Long id){

        return bookService.findBookById(id);
    }

    @PostMapping("/books/{authorId}/{publisherId}/books")
    public ResponseEntity<BookDTO> createBook(
            @PathVariable(value = "authorId") Long authorId,
            @PathVariable(value = "publisherId") Long publisherId,
            @Valid @RequestBody BookDTO bookRequest
    ) {
        return bookService.createBook(authorId, bookRequest, publisherId);
    }



}
