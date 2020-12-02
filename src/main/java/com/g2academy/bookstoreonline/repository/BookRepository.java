
package com.g2academy.bookstoreonline.repository;


import com.g2academy.bookstoreonline.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByAuthorNameContaining(String authorName);
    Book findAllByIsbn(String isbn);
    List<Book>  findAllByTitleContaining(String title);
    List<Book>  findAllByPublisher_NameContaining(String publisher);
    Book  findBookByTitleContaining(String title);
    Book  findBookByIsbnContaining(String isbn);
    Book  findBookByAuthorNameContaining(String name);
    Book  findBookByPublisherNameContaining(String name);
    Book  findTopByTitleContaining(String title);
    Book  findTopByAuthorNameContaining(String name);
    Book  findTopByPublisherNameContaining(String name);






}



//
//import com.g2academy.onlinebookstore.model.Book;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface BookRepository extends JpaRepository<Book, Long> {
////    Page<Book> findAuthorbyName(String authorName);
////
////    Optional<Book> findByIsbnAndAuthorName(String id, String authorName);
//}
