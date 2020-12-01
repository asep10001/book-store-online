package com.g2academy.bookstoreonline.repository;

import com.g2academy.bookstoreonline.model.ShoppingBasket;
import com.g2academy.bookstoreonline.model.ShoppingBasketBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingBasketRepository extends JpaRepository<ShoppingBasket, Long> {
    ShoppingBasket findByCustomer_Name(String name);
    List<ShoppingBasket> findShoppingBasketByShoppingBasketBooks(ShoppingBasketBook shoppingBasketBook);
}
