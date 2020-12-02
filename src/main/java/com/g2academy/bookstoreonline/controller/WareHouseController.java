package com.g2academy.bookstoreonline.controller;

import com.g2academy.bookstoreonline.service.WarehouseService;
import com.g2academy.bookstoreonline.service.dto.WareHouseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class WareHouseController {
    private final WarehouseService warehouseService;


    public WareHouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping("/warehouses/")
    public ResponseEntity<List<WareHouseDTO>> getAllBooks() {

        return warehouseService.getAllWareHouse();
    }

    @GetMapping("/warehouses/{id}")
    public ResponseEntity<WareHouseDTO> getAllBooks(@PathVariable(value = "id") Long id) {
        return warehouseService.findWareHouseById(id);
    }

    @PostMapping("/warehouses")
    public ResponseEntity<WareHouseDTO> saveWarehouse(
            @Valid @RequestBody WareHouseDTO request
    ) {
        return warehouseService.saveWareHouse(request);
    }

    @PostMapping("/warehouse/add_book/")
    public ResponseEntity<WareHouseDTO> addBookToWarehouse(
            @RequestParam(value = "bookId") Long id, @RequestParam(value = "warehouseId") Long warehouseId
    ) {
        return warehouseService.addBookToWarehouse(id, warehouseId);
    }

    @PutMapping("/warehouses/{id}")
    public ResponseEntity<WareHouseDTO> updateWarehouse(@Valid @RequestBody WareHouseDTO request, @PathVariable Long id) {
        return warehouseService.updateWarehouse(request, id);
    }

    @DeleteMapping("warehouses/delete/{id}")
    public void deleteAuthorById(@PathVariable Long id) {
        warehouseService.deleteWarehouse(id);
    }

    @GetMapping("warehouse/searh/title")
    public ResponseEntity<List<WareHouseDTO>> findByBookTitle(@RequestParam(value = "book_title") String title){
        return warehouseService.findByBookTitle(title);
    }

    @GetMapping("warehouse/searh/isbn")
    public ResponseEntity<List<WareHouseDTO>> findByBookIsbn(@RequestParam(value = "book_isbn") String isbn){
        return warehouseService.findByBookIsbn(isbn);
    }

    @GetMapping("warehouse/searh/author")
    public ResponseEntity<List<WareHouseDTO>> findByBookAuthor(@RequestParam(value = "author_name") String name){
        return warehouseService.findByBookAuthor(name);
    }
    @GetMapping("warehouse/searh/publisher")
    public ResponseEntity<List<WareHouseDTO>>findByBookPublisher(@RequestParam(value = "publisher_name") String name){
        return warehouseService.findByBookPublisher(name);
    }
}
