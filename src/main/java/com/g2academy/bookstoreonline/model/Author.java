package com.g2academy.bookstoreonline.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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


    public void addItem(Book item) {
        this.books.add(item);
        item.setAuthor(this);
    }
}
