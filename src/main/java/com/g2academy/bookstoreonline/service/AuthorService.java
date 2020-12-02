package com.g2academy.bookstoreonline.service;

import com.g2academy.bookstoreonline.model.*;
import com.g2academy.bookstoreonline.repository.*;
import com.g2academy.bookstoreonline.service.dto.AuthorDTO;
import com.g2academy.bookstoreonline.service.dto.BookDTO;
import com.g2academy.bookstoreonline.service.mapper.AuthorMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final ShoppingBasketBookRepository soppingShoppingBasketBookRepository;
    private final WarehouseBookRepository warehouseBookRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository, ShoppingBasketBookRepository soppingShoppingBasketBookRepository, WarehouseBookRepository warehouseBookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.soppingShoppingBasketBookRepository = soppingShoppingBasketBookRepository;
        this.warehouseBookRepository = warehouseBookRepository;
    }

    private Function<List<Author>, List<AuthorDTO>> toDtos() {
        return (x) -> AuthorMapper.INSTANCE.toDtos(x);
    }

    private Function<Author, AuthorDTO> toDto() {
        return (x) -> AuthorMapper.INSTANCE.toDto(x);
    }

    private Function<List<Author>, ResponseEntity<List<AuthorDTO>>> getAll() {
        return (x) -> new ResponseEntity<>(this.toDtos().apply(x), HttpStatus.OK);
    }

    private Function<Author, ResponseEntity<AuthorDTO>> getAnAuthor() {
        return (x) -> new ResponseEntity<>(this.toDto().apply(x), HttpStatus.OK);
    }

    public AuthorDTO save(Author aEntity) {
        return toDto().apply(authorRepository.save(aEntity));
    }

    public ResponseEntity<AuthorDTO> findById(Long id) {
        return this.getAnAuthor().apply(authorRepository.findById(id).get());
    }

    public ResponseEntity<AuthorDTO> findAllByNameContains(String name) {
        return this.getAnAuthor().apply(authorRepository.findAllByNameContains(name));
    }

    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return this.getAll().apply(authorRepository.findAll());
    }

    public ResponseEntity<AuthorDTO> saveAuthor(Author request, Long publisherId
    ) {
        Author aEntity = new Author();
        aEntity.setName(request.getName());
        aEntity.setAddress(request.getAddress());
        aEntity.setUrl(request.getUrl());
        List<Book> tempBookList = new ArrayList<>();
        Publisher pEntity = publisherRepository.findById(publisherId).get();
        for (Book book : request.getBooks()) {
            Book tempBook = new Book();
            tempBook.setIsbn(book.getIsbn());
            tempBook.setPrice(book.getPrice());
            tempBook.setTitle(book.getTitle());
            tempBook.setYear(book.getYear());
            tempBook.setAuthor(aEntity);
            tempBook.setPublisher(pEntity);
            tempBook.setPublisherName(pEntity.getName());
            tempBook.setAuthorName(aEntity.getName());
            tempBook.setAuthorAddress(aEntity.getAddress());
            aEntity.addItem(tempBook);
            pEntity.addItem(tempBook);
            tempBookList.add(tempBook);
        }

        aEntity.getBooks().addAll(tempBookList);
        authorRepository.save(aEntity);


        pEntity.getBooks().addAll(tempBookList);
        publisherRepository.save(pEntity);
        return this.getAnAuthor().apply(aEntity);
    }

    public ResponseEntity<AuthorDTO> updateAuthor(AuthorDTO authorDto, Long id) {
        return authorRepository.findById(id).map(author -> {
            author.setName(authorDto.getName());
            author.setAddress(authorDto.getAddress());
            author.setUrl(authorDto.getUrl());

            author.getBooks().forEach(book -> {
                book.setAuthorName(authorDto.getName());
                book.setAuthorAddress(authorDto.getAddress());
                book.setAuthor(author);
                bookRepository.save(book);
            });
            author.setBooks(author.getBooks());
            return this.getAnAuthor().apply(author);
        }).orElse(null);
    }

    public void delete(Long id) {
        authorRepository.findById(id).map(entity -> {
            authorRepository.delete(entity);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new NullPointerException("Authour with Name :  " + id + " not found")
        );
    }

    public ResponseEntity<AuthorDTO> addBook(BookDTO request, Long id, Long publisherId) {
        Publisher pbEntity = publisherRepository.findById(publisherId).orElse(null);
        return this.getAnAuthor().apply(authorRepository.findById(id).map(author -> {
            Book book = Book.builder()
                    .isbn(request.getIsbn())
                    .title(request.getTitle())
                    .year(request.getYear())
                    .price(request.getPrice())
                    .authorName(author.getName())
                    .authorAddress(author.getAddress())
                    .author(author)
                    .publisherName(pbEntity.getName())
                    .publisher(pbEntity)
                    .build();
            bookRepository.save(book);
            author.getBooks().add(book);
            return authorRepository.save(author);
        }).get());


    }

    public ResponseEntity<AuthorDTO> updateBook(BookDTO request, Long authorId, Long bookId) {
        Book bEntity = bookRepository.findById(bookId).orElse(null);
        return this.getAnAuthor().apply(authorRepository.findById(authorId).map(author -> {
            Book bookNow = author.getBooks().get(author.getBooks().indexOf(bEntity));
            bookNow.setYear(request.getYear());
            bookNow.setIsbn(request.getIsbn());
            bookNow.setTitle(request.getTitle());
            bookNow.setPrice(request.getPrice());
            bookRepository.save(bookNow);
            return authorRepository.save(author);
        }).get());
    }

    public ResponseEntity<AuthorDTO> deleteBook(Long authorId, Long bookId) {
        Book bEntity = bookRepository.findById(bookId).orElse(null);
        return this.getAnAuthor().apply(authorRepository.findById(authorId).map(author -> {
            author.getBooks().remove(bEntity);
            bookRepository.delete(bEntity);
            return authorRepository.save(author);
        }).get());
    }
}
