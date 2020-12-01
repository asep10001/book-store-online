package com.g2academy.bookstoreonline.service;

import com.g2academy.bookstoreonline.model.*;
import com.g2academy.bookstoreonline.repository.BookRepository;
import com.g2academy.bookstoreonline.repository.WarehouseBookRepository;
import com.g2academy.bookstoreonline.repository.WarehouseRepository;
import com.g2academy.bookstoreonline.service.dto.AuthorDTO;
import com.g2academy.bookstoreonline.service.dto.WareHouseDTO;
import com.g2academy.bookstoreonline.service.mapper.WareHouseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseBookRepository warehouseBookRepository;
    private final BookRepository bookRepository;

    public WarehouseService(WarehouseRepository warehouseRepository, WarehouseBookRepository warehouseBookRepository, BookRepository bookRepository) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseBookRepository = warehouseBookRepository;
        this.bookRepository = bookRepository;
    }

    private Function<List<WareHouse>, List<WareHouseDTO>> toDtos() {
        return (x) -> WareHouseMapper.INSTANCE.toDtos(x);
    }

    private Function<WareHouse, WareHouseDTO> toDto() {
        return (x) -> WareHouseMapper.INSTANCE.toDto(x);
    }

    private Function<List<WareHouse>, ResponseEntity<List<WareHouseDTO>>> getAll() {
        return (x) -> new ResponseEntity<>(this.toDtos().apply(x), HttpStatus.OK);
    }

    private Function<WareHouse, ResponseEntity<WareHouseDTO>> getAWareHouse() {
        return (x) -> new ResponseEntity<>(this.toDto().apply(x), HttpStatus.OK);
    }

    public WareHouse save(WareHouse wareHouseEntity) {
        return warehouseRepository.save(wareHouseEntity);
    }

    public ResponseEntity<WareHouseDTO> findWareHouseById(Long id) {

        return this.getAWareHouse().apply(warehouseRepository.findById(id).get());
    }

    public ResponseEntity<List<WareHouseDTO>> getAllWareHouse() {

        return this.getAll().apply(warehouseRepository.findAll());
    }

    public ResponseEntity<WareHouseDTO> saveWareHouse(WareHouseDTO request
    ) {
        WareHouse wEntity = WareHouse.builder()
                .address(request.getAddress())
                .phone(request.getPhone())
                .build();
        return this.getAWareHouse().apply(this.save(wEntity));
    }

    public ResponseEntity<WareHouseDTO> updateWarehouse(WareHouseDTO warehouseDTO, Long id) {
        return this.getAWareHouse().apply(warehouseRepository.findById(id).map(wareHouse -> {
            wareHouse.setAddress(warehouseDTO.getAddress());
            wareHouse.setPhone(warehouseDTO.getPhone());

            wareHouse.getWrBooks().forEach(book -> {
                book.setWarehouseAdress(warehouseDTO.getAddress());
                warehouseBookRepository.save(book);
            });
            wareHouse.getWrBooks().clear();
            wareHouse.getWrBooks().addAll(wareHouse.getWrBooks());
            return this.save(wareHouse);
        }).get());
    }

    public void deleteWarehouse(Long id) {
        warehouseRepository.findById(id).map(entity -> {
            warehouseRepository.delete(entity);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new NullPointerException("Warehouse with id :  " + id + " not found")
        );
    }

    public ResponseEntity<WareHouseDTO> findByBookTitle(String booktitle) {
        return this.getAWareHouse().apply(
                warehouseRepository.findAllByWrBooksIn(
                        warehouseBookRepository.findAllByWrBook(
                                bookRepository.findBookByTitleContaining(booktitle))));
    }

    public ResponseEntity<WareHouseDTO> findByBookIsbn(String bookIsbn) {
        return this.getAWareHouse().apply(
                warehouseRepository.findAllByWrBooksIn(
                        warehouseBookRepository.findAllByWrBook(
                                bookRepository.findBookByIsbnContaining(bookIsbn))));
    }

    public ResponseEntity<WareHouseDTO> findByBookAuthor(String name) {
        return this.getAWareHouse().apply(
                warehouseRepository.findAllByWrBooksIn(
                warehouseBookRepository.findAllByWrBook(
                        bookRepository.findBookByAuthorNameContaining(name))));
    }

    public ResponseEntity<WareHouseDTO> findByBookPublisher(String name) {
        return this.getAWareHouse().apply(
                warehouseRepository.findAllByWrBooksIn(
                        warehouseBookRepository.findAllByWrBook(
                                bookRepository.findBookByPublisherNameContaining(name))));
    }

    public ResponseEntity<WareHouseDTO> addBookToWarehouse(Long bookId, Long warehouseId) {

        Book bentity = bookRepository.findById(bookId).orElse(null);
        WareHouse whEntity = warehouseRepository.findById(warehouseId).orElse(null);
        WarehouseBook warehouseBook = WarehouseBook.builder()
                .bookISBN(bentity.getIsbn())
                .warehouseAdress(whEntity.getAddress())
                .wrBook(bentity)
                .warehouse(whEntity)
                .build();
        bentity.getWarehouseBook().add(warehouseBook);

        warehouseBookRepository.save(warehouseBook);
        whEntity.getWrBooks().add(warehouseBook);

        return this.getAWareHouse().apply(this.save(whEntity));
    }
}
