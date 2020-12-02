package com.g2academy.bookstoreonline.service.mapper;

import com.g2academy.bookstoreonline.model.Author;
import com.g2academy.bookstoreonline.service.dto.AuthorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = BookMapper.class)
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    @Mapping(target = "id", expression = "java(entity.getAuthorId())")
    @Mapping(target= "books", expression = "java(entity.changeToBookDTO())")
    AuthorDTO toDto(Author entity);
    Author toEntity(AuthorDTO dto);

    List<AuthorDTO> toDtos(List<Author> entities);
    List<Author> toEntities(List<AuthorDTO> dtos);
}
