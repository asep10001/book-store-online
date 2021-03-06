package com.g2academy.bookstoreonline.repository;

import com.g2academy.bookstoreonline.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findAllByNameContains(String name);


}
