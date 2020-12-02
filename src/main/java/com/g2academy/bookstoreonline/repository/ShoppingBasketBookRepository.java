package com.g2academy.bookstoreonline.repository;

import com.g2academy.bookstoreonline.model.Book;
import com.g2academy.bookstoreonline.model.ShoppingBasket;
import com.g2academy.bookstoreonline.model.ShoppingBasketBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingBasketBookRepository extends JpaRepository<ShoppingBasketBook, Long> {
    ShoppingBasketBook findByShoppingBasket(ShoppingBasket a);
    ShoppingBasketBook findByShoppingBasketBook(Book b);
}
