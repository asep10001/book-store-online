package com.g2academy.bookstoreonline.controller;


import com.g2academy.bookstoreonline.model.Author;
import com.g2academy.bookstoreonline.model.ShoppingBasket;
import com.g2academy.bookstoreonline.service.ShoppingBasketService;
import com.g2academy.bookstoreonline.service.dto.ShoppingBasketDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
public class ShoppingBasketController {
    private final ShoppingBasketService shoppingBasketService;

    public ShoppingBasketController(ShoppingBasketService shoppingBasketService) {
        this.shoppingBasketService = shoppingBasketService;
    }

    @GetMapping("/shopping_basket/")
    public ResponseEntity<List<ShoppingBasketDTO>> getAllShoppingBasket() {
        return shoppingBasketService.findAllShoppingBasket();
    }

    @PostMapping("/shopping_basket/{customerId}")
    public ResponseEntity<ShoppingBasketDTO> saveShoppingBasket(
            @PathVariable(value = "customerId") Long id
    ) {
        return shoppingBasketService.addShoppingBasket(id);
    }

    @PostMapping("/shopping_basket/add_book/")
    public ResponseEntity<ShoppingBasketDTO> addBookToShoppingBasket(
            @RequestParam(value = "bookId") Long id, @RequestParam(value = "shoppingBasketId") Long shoppingLong
    ) {
        return shoppingBasketService.addBook(id, shoppingLong);
    }

    @DeleteMapping("shopping_basket/delete_book/")
    public void deleteBook(
            @RequestParam(value = "book") Long bookId) {
        shoppingBasketService.deleteBook(bookId);
    }

    @GetMapping("shopping_basket/search/customer_name/")
    public ResponseEntity<ShoppingBasketDTO> findByCustomerName(
            @RequestParam(value = "customer_name") String name) {
        return shoppingBasketService.findByCustomerName(name);
    }

    @GetMapping("shopping_basket/search/book_isbn/")
    public ResponseEntity<List<ShoppingBasketDTO>> findByBookIsbn(
            @RequestParam(value = "book_isbn") String isbn) {
        return shoppingBasketService.findByBookIsbn(isbn);
    }

    @GetMapping("shopping_basket/search/book_title/")
    public ResponseEntity<List<ShoppingBasketDTO>> findByBookTitle(
            @RequestParam(value = "book_title") String title) {
        return shoppingBasketService.findByBookTitle(title);
    }


}
