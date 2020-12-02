package com.g2academy.bookstoreonline.service;

import com.g2academy.bookstoreonline.model.Author;
import com.g2academy.bookstoreonline.model.Book;
import com.g2academy.bookstoreonline.model.Publisher;
import com.g2academy.bookstoreonline.repository.AuthorRepository;
import com.g2academy.bookstoreonline.repository.BookRepository;
import com.g2academy.bookstoreonline.repository.PublisherRepository;
import com.g2academy.bookstoreonline.service.dto.BookDTO;
import com.g2academy.bookstoreonline.service.dto.PublisherDTO;
import com.g2academy.bookstoreonline.service.mapper.BookMapper;
import com.g2academy.bookstoreonline.service.mapper.PublisherMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    private Function<List<Book>, List<BookDTO>> toDtos()
    {
        return (x)  -> BookMapper.INSTANCE.toDtos(x);
    }

    private Function<Book, BookDTO> toDto(){
        return (x) -> BookMapper.INSTANCE.toDto(x);
    }

    private Function<Book, ResponseEntity<BookDTO>> getABook(){
        return (x) -> new ResponseEntity<>(this.toDto().apply(x), HttpStatus.OK);
    }

    private Function<List<Book>, ResponseEntity<List<BookDTO>>> getAllBooks(){
        return (x) -> new ResponseEntity<>(this.toDtos().apply(x), HttpStatus.OK);
    }
    public ResponseEntity<BookDTO> findBookById(Long id){return this.getABook().apply(bookRepository.findById(id).get());}

    public ResponseEntity<List<BookDTO>> findAllbook() {
        return this.getAllBooks().apply(bookRepository.findAll());
    }

    public ResponseEntity<List<BookDTO>> findAllBookByAuthorNameContaining(String authorName){
        return this.getAllBooks().apply(bookRepository.findAllByAuthorNameContaining(authorName));
    }

    public ResponseEntity<BookDTO> findAllBookByIsbn(String isbn){
        return this.getABook().apply(bookRepository.findAllByIsbn(isbn));
    }

    public ResponseEntity<List<BookDTO>>findAllBookByTitle(String title){
        return this.getAllBooks().apply(bookRepository.findAllByTitleContaining(title));
    }

    public ResponseEntity<List<BookDTO>> findAllBookByPublisher(String publisher){
        return this.getAllBooks().apply(bookRepository.findAllByPublisher_NameContaining(publisher));
    }

    public ResponseEntity<BookDTO> createBook(Long authorId, BookDTO bookRequest, Long publisherId
    ) {
        Author aEntity = authorRepository.findById(authorId).orElse(null);
        Publisher pEntity = publisherRepository.findById(publisherId).orElse(null);
        Book book = Book.builder()
                .authorAddress(aEntity.getAddress())
                .authorName(aEntity.getName())
                .isbn(bookRequest.getIsbn())
                .publisherName(pEntity.getName())
                .title(bookRequest.getTitle())
                .price(bookRequest.getPrice())
                .year(bookRequest.getYear())
                .author(aEntity)
                .build();
        aEntity.addItem(book);
        pEntity.addItem(book);

        return this.getABook().apply(bookRepository.save(book));
    }
}
