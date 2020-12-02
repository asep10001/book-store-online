package com.g2academy.bookstoreonline.service;


import com.g2academy.bookstoreonline.model.*;
import com.g2academy.bookstoreonline.repository.BookRepository;
import com.g2academy.bookstoreonline.repository.CustomerRepository;
import com.g2academy.bookstoreonline.repository.ShoppingBasketBookRepository;
import com.g2academy.bookstoreonline.repository.ShoppingBasketRepository;
import com.g2academy.bookstoreonline.service.dto.CustomerDTO;
import com.g2academy.bookstoreonline.service.dto.PublisherDTO;
import com.g2academy.bookstoreonline.service.dto.ShoppingBasketDTO;
import com.g2academy.bookstoreonline.service.mapper.CustomerMapper;
import com.g2academy.bookstoreonline.service.mapper.PublisherMapper;
import com.g2academy.bookstoreonline.service.mapper.ShoppingBasketMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class ShoppingBasketService {

    private final ShoppingBasketRepository shoppingBasketRepository;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final ShoppingBasketBookRepository shoppingBasketBookRepository;


    public ShoppingBasketService(ShoppingBasketRepository shoppingBasketRepository, BookRepository bookRepository, CustomerRepository customerRepository, ShoppingBasketBookRepository shoppingBasketBookRepository) {
        this.shoppingBasketRepository = shoppingBasketRepository;
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
        this.shoppingBasketBookRepository = shoppingBasketBookRepository;
    }

    private Function<List<ShoppingBasket>, List<ShoppingBasketDTO>> toDtos()
    {
        return (x)  -> ShoppingBasketMapper.INSTANCE.toDtos(x);
    }

    private Function<ShoppingBasket, ShoppingBasketDTO> toDto(){
        return (x) -> ShoppingBasketMapper.INSTANCE.toDto(x);
    }

    private Function<ShoppingBasket, ResponseEntity<ShoppingBasketDTO>> getAShoppingBasket() {
        return (x) -> new ResponseEntity<>(this.toDto().apply(x), HttpStatus.OK);
    }

    private Function<List<ShoppingBasket>, ResponseEntity<List<ShoppingBasketDTO>>> getAllShoppingBaskets() {
        return (x) -> new ResponseEntity<>(this.toDtos().apply(x), HttpStatus.OK);
    }

    public ShoppingBasket save(ShoppingBasket shoppingBasketEntity) {
        return shoppingBasketRepository.save(shoppingBasketEntity);
    }

    public ResponseEntity<List<ShoppingBasketDTO>> findAllShoppingBasket() {
        return this.getAllShoppingBaskets().apply(shoppingBasketRepository.findAll());
    }

    public ResponseEntity<ShoppingBasketDTO> addShoppingBasket(Long customerId) {
        Customer cEntity = customerRepository.findById(customerId).orElse(null);

        ShoppingBasket sEntity = ShoppingBasket.builder()
                .email(cEntity.getEmail())
                .customer(cEntity)
                .shoppingBasketBooks(null)
                .build();
        this.save(sEntity);
        return this.getAShoppingBasket().apply(sEntity);
    }

    public ResponseEntity<ShoppingBasketDTO>  addBook(Long bookId, Long shoppingBasketId) {

        Book bentity = bookRepository.findById(bookId).orElse(null);
        ShoppingBasket shEntity = shoppingBasketRepository.findById(shoppingBasketId).orElse(null);
        ShoppingBasketBook shoppingBasketBook = ShoppingBasketBook.builder()
                .shoppingBasket(shEntity)
                .shoppingBasketBook(bentity)
                .bookISBN(bentity.getIsbn())
                .build();
        bentity.getShoppingBasketBooks().add(shoppingBasketBook);

        shEntity.getShoppingBasketBooks().add(shoppingBasketBook);
        shoppingBasketBookRepository.save(shoppingBasketBook);

        return this.getAShoppingBasket().apply(this.save(shEntity));


    }

    public void deleteBook(Long bookid) {
        shoppingBasketBookRepository.delete(
                shoppingBasketBookRepository.findByShoppingBasketBook(
                        bookRepository.findById(bookid).get()));
    }

    public ResponseEntity<ShoppingBasketDTO> findByCustomerName(String name) {
        return this.getAShoppingBasket().apply(shoppingBasketRepository.findByCustomer_Name(name));
    }

    public ResponseEntity<List<ShoppingBasketDTO>> findByBookIsbn(String bookIsbn) {
        return this.getAllShoppingBaskets().apply(shoppingBasketRepository.findShoppingBasketByShoppingBasketBooks(
                shoppingBasketBookRepository.findByShoppingBasketBook(
                bookRepository.findBookByIsbnContaining(bookIsbn))));
    }

    public ResponseEntity<List<ShoppingBasketDTO>> findByBookTitle(String bookTitle) {
        return this.getAllShoppingBaskets().apply(shoppingBasketRepository.findShoppingBasketByShoppingBasketBooks(
                shoppingBasketBookRepository.findByShoppingBasketBook(
                        bookRepository.findBookByTitleContaining(bookTitle))));
    }

//    public Optional<ShoppingBasket> updateBasket(ShoppingBasketDto shoppingBasketDto, Long id) {
//
//
//            shoppingBasket.setEmail(shoppingBasketDto.getEmail());
//            shoppingBasket.setBooks(shoppingBasket.getBooks());
//
//            return this.save(shoppingBasket);
//
//        });
//    }

}
