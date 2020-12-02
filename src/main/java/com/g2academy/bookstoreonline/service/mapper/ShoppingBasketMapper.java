package com.g2academy.bookstoreonline.service.mapper;

import com.g2academy.bookstoreonline.model.ShoppingBasket;
import com.g2academy.bookstoreonline.service.dto.ShoppingBasketDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = ShoppingBasketMapper.class)
public interface ShoppingBasketMapper {

    ShoppingBasketMapper INSTANCE = Mappers.getMapper(ShoppingBasketMapper.class);

    @Mapping(target = "id", expression = "java(entity.getShopBasId())")
    @Mapping(target = "customerId", expression = "java(entity.getCustomer().getCustomerId())")
    @Mapping(target = "customerName", expression = "java(entity.getCustomer().getName())")
    @Mapping(target = "customerEmail", expression = "java(entity.getCustomer().getEmail())")


    ShoppingBasketDTO toDto(ShoppingBasket entity);
    ShoppingBasket toEntity(ShoppingBasketDTO dto);

    List<ShoppingBasketDTO> toDtos(List<ShoppingBasket> entities);
    List<ShoppingBasket> toEntities(List<ShoppingBasketDTO> dtos);
}
