package com.g2academy.bookstoreonline.repository;

import com.g2academy.bookstoreonline.model.Book;
import com.g2academy.bookstoreonline.model.WareHouse;
import com.g2academy.bookstoreonline.model.WarehouseBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<WareHouse, Long> {
    List<WareHouse> findAllByWrBooksIn(List<WarehouseBook> book);

    List<WareHouse> findAllByWrBooksContaining(WarehouseBook allByWrBook);
}
