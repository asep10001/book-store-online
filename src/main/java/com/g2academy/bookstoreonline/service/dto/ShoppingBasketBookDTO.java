package com.g2academy.bookstoreonline.service.dto;

import com.g2academy.bookstoreonline.model.Book;
import com.g2academy.bookstoreonline.model.ShoppingBasket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;


@Value
@Builder
@AllArgsConstructor
public class ShoppingBasketBookDTO {
    private Long id;
    private String bookISBN;
    private Book shoppingBasketBook;
    private ShoppingBasket shoppingBasket;
}
