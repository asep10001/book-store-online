package com.g2academy.bookstoreonline.service.dto;

import com.g2academy.bookstoreonline.model.Author;
import com.g2academy.bookstoreonline.model.ShoppingBasketBook;
import com.g2academy.bookstoreonline.model.WarehouseBook;
import lombok.*;

import java.time.YearMonth;
import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class BookDTO {

    private Long id;
    private String isbn;

    private String publisherName;

    private String authorName;

    private String authorAddress;

    private YearMonth year;

    private String title;

    private Double price;


    private Author author;
    private List<WarehouseBook> warehouseBook;
    private List<ShoppingBasketBook> shoppingBasketBooks;
}
