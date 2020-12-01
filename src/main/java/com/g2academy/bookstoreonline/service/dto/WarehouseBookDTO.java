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
    private Long id;
    private String warehouseAddress;
    private String bookISBN;
    private Book wrBooks;
    private WareHouse warehouse;
//    private Integer count;
}
