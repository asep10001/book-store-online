package com.g2academy.bookstoreonline.service;

import com.g2academy.bookstoreonline.model.Book;
import com.g2academy.bookstoreonline.model.Customer;
import com.g2academy.bookstoreonline.model.ShoppingBasket;
import com.g2academy.bookstoreonline.repository.CustomerRepository;
import com.g2academy.bookstoreonline.repository.ShoppingBasketRepository;
import com.g2academy.bookstoreonline.service.dto.BookDTO;
import com.g2academy.bookstoreonline.service.dto.CustomerDTO;
import com.g2academy.bookstoreonline.service.mapper.BookMapper;
import com.g2academy.bookstoreonline.service.mapper.CustomerMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class CustomerService {


    private final CustomerRepository customerRepository;
    private final ShoppingBasketRepository shoppingBasketRepository;


    public CustomerService(CustomerRepository customerRepository, ShoppingBasketRepository shoppingBasketRepository) {
        this.customerRepository = customerRepository;
        this.shoppingBasketRepository = shoppingBasketRepository;
    }

    private Function<List<Customer>, List<CustomerDTO>> toDtos() {
        return (x) -> CustomerMapper.INSTANCE.toDtos(x);
    }

    private Function<Customer, CustomerDTO> toDto() {
        return (x) -> CustomerMapper.INSTANCE.toDto(x);
    }

    private Function<Customer, ResponseEntity<CustomerDTO>> getACustomer() {
        return (x) -> new ResponseEntity<>(this.toDto().apply(x), HttpStatus.OK);
    }

    private Function<List<Customer>, ResponseEntity<List<CustomerDTO>>> findAllCustomers() {
        return (x) -> new ResponseEntity<>(this.toDtos().apply(x), HttpStatus.OK);
    }

    public Customer save(Customer cEntity) {
        return customerRepository.save(cEntity);
    }

    public ResponseEntity<CustomerDTO> findById(Long id) {
        return this.getACustomer().apply(customerRepository.findById(id).get());
    }

    public ResponseEntity<CustomerDTO> findByEmail(String email) {
        return this.getACustomer().apply(customerRepository.findCustomerByEmail(email).get());
    }

    public ResponseEntity<List<CustomerDTO>> findByName(String name) {
        return this.findAllCustomers().apply(customerRepository.findAllByNameContaining(name));
    }

    public List<CustomerDTO> getAllCustomers() {

        return toDtos().apply(customerRepository.findAll());
    }

    public ResponseEntity<CustomerDTO> saveCustomers(Customer request
    ) {

        Customer cEntity = Customer.builder()
                .email(request.getEmail())
                .name(request.getName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .shoppingBaskets(request.getShoppingBaskets())
                .build();
        this.save(cEntity);

        return this.getACustomer().apply(cEntity);
    }

    public ResponseEntity<CustomerDTO> updateCustomer(CustomerDTO customerDto, Long id) {
        return this.getACustomer().apply(customerRepository.findById(id).map(customer -> {
            customer.setName(customerDto.getName());
            customer.setAddress(customerDto.getAddress());
            customer.setEmail(customerDto.getEmail());
            customer.setPhone(customerDto.getPhone());

            customer.getShoppingBaskets().forEach(shoppingBasket -> {
                shoppingBasket.setEmail(customerDto.getEmail());
                shoppingBasket.setCustomer(customer);
                shoppingBasketRepository.save(shoppingBasket);
            });
            customer.setShoppingBaskets(customer.getShoppingBaskets());
            return this.save(customer);
        }).get());


    }

    public void deleteCustomer(Long id) {
        customerRepository.findById(id).map(entity -> {
            customerRepository.delete(entity);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new NullPointerException("Customer with Id :  " + id + " not found")
        );
    }

}
