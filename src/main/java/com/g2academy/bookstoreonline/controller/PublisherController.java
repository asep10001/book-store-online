package com.g2academy.bookstoreonline.controller;

import com.g2academy.bookstoreonline.model.Publisher;
import com.g2academy.bookstoreonline.service.PublisherService;
import com.g2academy.bookstoreonline.service.dto.PublisherDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class PublisherController {
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/publishers/")
    public ResponseEntity<List<PublisherDTO>> getAllBooks() {
        return publisherService.getAllPublisher();
    }

    @PostMapping("/publishers")
    public ResponseEntity<PublisherDTO> savePublisher(
            @Valid @RequestBody PublisherDTO request
    ) {
        return publisherService.savePublisher(request);
    }

    @PutMapping("/publishers/{id}")
    public ResponseEntity<PublisherDTO> updatePublisher(@Valid @RequestBody PublisherDTO request, @PathVariable Long id) {
        return publisherService.updatePublisher(request, id);
    }

    @DeleteMapping("publishers/delete/{id}")
    public void deleteAuthorById(@PathVariable Long id) {
        publisherService.deletePublisher(id);
    }
}
