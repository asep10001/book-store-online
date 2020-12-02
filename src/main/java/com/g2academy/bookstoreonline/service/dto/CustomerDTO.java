package com.g2academy.bookstoreonline.service.dto;

import com.g2academy.bookstoreonline.model.ShoppingBasket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class CustomerDTO {
    private Long id;
    private String email;
    private String name;
    private String address;
    private String phone;
//    private List<ShoppingBasket> shoppingBaskets;

}
