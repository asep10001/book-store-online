package com.g2academy.bookstoreonline.controller;

import com.g2academy.bookstoreonline.model.Customer;
import com.g2academy.bookstoreonline.service.CustomerService;
import com.g2academy.bookstoreonline.service.dto.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return new ResponseEntity<List<CustomerDTO>>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerDTO> findCustomerByID(@PathVariable(value = "id") Long id) {
        return customerService.findById(id);
    }

    @GetMapping("/customers/name/")
    public ResponseEntity<List<CustomerDTO>> findCustomerByName(@RequestParam(value = "name") String name) {
        return customerService.findByName(name);
    }

    @GetMapping("/customers/email/")
    public ResponseEntity<CustomerDTO> findCustomerByEmail(@RequestParam(value = "email") String name) {
        return customerService.findByEmail(name);
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerDTO> saveCustomer(
            @Valid @RequestBody Customer request
    ) {
        return customerService.saveCustomers(request);
    }

    @PutMapping("/customers/customer/{id}")
    public ResponseEntity<CustomerDTO> updateACustomerById(@Valid @RequestBody CustomerDTO customerDto, @PathVariable(value = "id") Long id){
        return customerService.updateCustomer(customerDto, id);
    }

    @DeleteMapping("/customers/delete/{id}")
    public void deleteCustomer(@PathVariable(value = "id") Long id){
        customerService.deleteCustomer(id);
    }
}
