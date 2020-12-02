package com.g2academy.bookstoreonline.service;


import com.g2academy.bookstoreonline.model.Author;
import com.g2academy.bookstoreonline.model.Customer;
import com.g2academy.bookstoreonline.model.Publisher;
import com.g2academy.bookstoreonline.repository.BookRepository;
import com.g2academy.bookstoreonline.repository.PublisherRepository;
import com.g2academy.bookstoreonline.service.dto.AuthorDTO;
import com.g2academy.bookstoreonline.service.dto.CustomerDTO;
import com.g2academy.bookstoreonline.service.dto.PublisherDTO;
import com.g2academy.bookstoreonline.service.mapper.AuthorMapper;
import com.g2academy.bookstoreonline.service.mapper.PublisherMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;


    public PublisherService(PublisherRepository publisherRepository, BookRepository bookRepository) {
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }

    private Function<List<Publisher>, List<PublisherDTO>> toDtos()
    {
        return (x)  -> PublisherMapper.INSTANCE.toDtos(x);
    }

    private Function<Publisher, PublisherDTO> toDto(){
        return (x) -> PublisherMapper.INSTANCE.toDto(x);
    }

    private Function<Publisher, ResponseEntity<PublisherDTO>> getAPublisher() {
        return (x) -> new ResponseEntity<>(this.toDto().apply(x), HttpStatus.OK);
    }

    private Function<List<Publisher>, ResponseEntity<List<PublisherDTO>>> findAllPublishers() {
        return (x) -> new ResponseEntity<>(this.toDtos().apply(x), HttpStatus.OK);
    }
    public Publisher save(Publisher publisherEntity) {
        return publisherRepository.save(publisherEntity);
    }

    public ResponseEntity<PublisherDTO> findPublisherById(Long id) {
        return this.getAPublisher().apply(publisherRepository.findById(id).get());
    }

    public ResponseEntity<List<PublisherDTO>> getAllPublisher() {

        return this.findAllPublishers().apply(publisherRepository.findAll());
    }

    public ResponseEntity<PublisherDTO> savePublisher(PublisherDTO request
    ) {
        Publisher pEntity = Publisher.builder()
                .name(request.getName())
                .address(request.getAddress())
                .url(request.getUrl())
                .phone(request.getPhone())
                .build();
        return this.getAPublisher().apply(this.save(pEntity));
    }

    public ResponseEntity<PublisherDTO> updatePublisher(PublisherDTO request, Long publisherId
    ) {
        return this.getAPublisher().apply(publisherRepository.findById(publisherId).map(publisher -> {
            publisher.setName(request.getName());
            publisher.setAddress(request.getAddress());
            publisher.setPhone(request.getPhone());
            publisher.setUrl(request.getUrl());
            publisher.getBooks().forEach(book -> {
                book.setPublisherName(request.getName());
            });
            publisher.setBooks(publisher.getBooks());
            return this.save(publisher);
        }).get());
    }

    public void deletePublisher(Long id) {
        publisherRepository.findById(id).map(entity -> {
            publisherRepository.delete(entity);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new NullPointerException("Publisher with Id :  " + id + " not found")
        );
    }
}
