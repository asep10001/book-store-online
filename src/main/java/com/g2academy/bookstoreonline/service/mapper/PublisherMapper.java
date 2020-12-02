package com.g2academy.bookstoreonline.service.mapper;

import com.g2academy.bookstoreonline.model.Publisher;
import com.g2academy.bookstoreonline.service.dto.PublisherDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = BookMapper.class)
public interface PublisherMapper {


    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    @Mapping(target = "id", expression = "java(entity.getPublisherId())")
    PublisherDTO toDto(Publisher entity);
    Publisher toEntity(PublisherDTO dto);

    List<PublisherDTO> toDtos(List<Publisher> entities);
    List<Publisher> toEntities(List<PublisherDTO> dtos);
}
