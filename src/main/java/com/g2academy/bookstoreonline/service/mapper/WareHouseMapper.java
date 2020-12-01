package com.g2academy.bookstoreonline.service.mapper;

import com.g2academy.bookstoreonline.model.WareHouse;
import com.g2academy.bookstoreonline.service.dto.WareHouseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = BookMapper.class)
public interface WareHouseMapper {
    WareHouseMapper INSTANCE = Mappers.getMapper(WareHouseMapper.class);

    WareHouseDTO toDto(WareHouse entity);
    WareHouse toEntity(WareHouseDTO dto);

    List<WareHouseDTO> toDtos(List<WareHouse> entities);
    List<WareHouse> toEntities(List<WareHouseDTO> dtos);
}
