package com.g2academy.bookstoreonline.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.g2academy.bookstoreonline.service.dto.AuthorDTO;
import com.g2academy.bookstoreonline.service.dto.BookDTO;
import com.g2academy.bookstoreonline.service.mapper.AuthorMapper;
import com.g2academy.bookstoreonline.service.mapper.BookMapper;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "author")
@Audited
@AuditTable("author_audit")
public class Author extends BaseEntity<String> implements Serializable {


    private static final long serialVersionUID = 1L;

    private Function<List<Book>, List<BookDTO>> toDtos() {
        return (x) -> BookMapper.INSTANCE.toDtos(x);
    }

    public Long getAuthorId(){
        return id;
    }
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "adress", nullable = false)
    private String address;

    @Column(name = "url", nullable = false)
    private String url;

    @JsonManagedReference("author")
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "author",
            orphanRemoval = true
    )
    private List<Book> books = new ArrayList<>();

    public List<BookDTO> changeToBookDTO(){
        return this.toDtos().apply(books);
    }

    public void addItem(Book item) {
        this.books.add(item);
        item.setAuthor(this);
    }
}
