package com.g2academy.bookstoreonline.service.dto;

import com.g2academy.bookstoreonline.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class PublisherDTO {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String url;
    private List<Book> books;
}
