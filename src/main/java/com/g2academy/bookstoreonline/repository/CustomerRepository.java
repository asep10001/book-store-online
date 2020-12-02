package com.g2academy.bookstoreonline.repository;

import com.g2academy.bookstoreonline.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByEmail(String Email);
    List<Customer> findAllByNameContaining(String name);
    Customer findByNameContains(String name);
}
