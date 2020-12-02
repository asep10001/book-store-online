package com.g2academy.bookstoreonline.service.dto;


import com.g2academy.bookstoreonline.model.Book;
import com.g2academy.bookstoreonline.model.WareHouse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class WarehouseBookDTO {
    private String bookISBN;
    private String bookTitle;
    private WareHouse warehouse;
//    private Integer count;
}
