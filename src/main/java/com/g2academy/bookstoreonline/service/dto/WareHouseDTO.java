package com.g2academy.bookstoreonline.service.dto;

import com.g2academy.bookstoreonline.model.Book;
import com.g2academy.bookstoreonline.model.WarehouseBook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Value
@Getter
@Builder
@AllArgsConstructor
public class WareHouseDTO {
    private Long id;
    private String phone;
    private String address;
    private List<WarehouseBook> wrBooks = new ArrayList<>();
}
