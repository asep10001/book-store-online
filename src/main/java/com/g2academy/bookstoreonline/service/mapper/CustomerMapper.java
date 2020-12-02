package com.g2academy.bookstoreonline.service.mapper;

import com.g2academy.bookstoreonline.model.Customer;
import com.g2academy.bookstoreonline.service.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = ShoppingBasketMapper.class)
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "id", expression = "java(entity.getCustomerId())")
//    @Mapping(target = "shoppingBaskets", ignore = true)
    CustomerDTO toDto(Customer entity);
    Customer toEntity(CustomerDTO dto);

    List<CustomerDTO> toDtos(List<Customer> entities);
    List<Customer> toEntities(List<CustomerDTO> dtos);
}
