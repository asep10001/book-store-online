package com.g2academy.bookstoreonline.service.mapper;

import com.g2academy.bookstoreonline.model.Author;
import com.g2academy.bookstoreonline.model.Book;
import com.g2academy.bookstoreonline.service.dto.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {AuthorMapper.class, PublisherMapper.class})
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "id", expression = "java(entity.getBookId())")
    @Mapping(source = "author", target = "authorId", qualifiedByName = "getAuthorId")
    @Mapping(source= "author", target = "authorName", qualifiedByName = "getAuthorName")
    @Mapping(source = "author", target = "authorAddress", qualifiedByName = "getAuthorAddress")
    BookDTO toDto(Book entity);
    Book toEntity(BookDTO dto);

    List<BookDTO> toDtos(List<Book> entities);
    List<Book> toEntities(List<BookDTO> dtos);

    @Named("getAuthorId")
    default Long authorEntityGetAuthorId(Author authorEntity){
        return authorEntity.getAuthorId();
    }

    @Named("getAuthorName")
    default String authorEntityGetAuthorName(Author authorEntity){
        return authorEntity.getName();
    }

    @Named("getAuthorAddress")
    default String authorEntityGetAuthorAddress(Author authorEntity){
        return authorEntity.getAddress();
    }
}
