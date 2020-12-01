package com.g2academy.bookstoreonline.service.dto;

import com.g2academy.bookstoreonline.model.Book;
import com.g2academy.bookstoreonline.model.Customer;
import com.g2academy.bookstoreonline.model.ShoppingBasket;
import com.g2academy.bookstoreonline.model.ShoppingBasketBook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class ShoppingBasketDTO {
    private Long id;
    private String email;
    private Customer customer;
    private List<ShoppingBasketBook> shoppingBasketBooks;
}
