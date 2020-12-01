package com.g2academy.bookstoreonline.service.mapper;

import com.g2academy.bookstoreonline.model.Book;
import com.g2academy.bookstoreonline.service.dto.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {AuthorMapper.class, PublisherMapper.class})
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDTO toDto(Book entity);
    Book toEntity(BookDTO dto);

    List<BookDTO> toDtos(List<Book> entities);
    List<Book> toEntities(List<BookDTO> dtos);
}
