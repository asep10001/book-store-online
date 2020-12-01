package com.g2academy.bookstoreonline.service.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.g2academy.bookstoreonline.model.Author;
import com.g2academy.bookstoreonline.model.Book;
import com.g2academy.bookstoreonline.model.converter.YearMonthIntegerAttributeConverter;
import lombok.*;

import javax.persistence.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class AuthorDTO {
    private Long id;
    private String name;

    private String address;

    private String url;

    private List<Book> books;
}
