package com.g2academy.bookstoreonline.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.g2academy.bookstoreonline.model.converter.YearMonthIntegerAttributeConverter;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Audited
@AuditTable("book_audit")
public class Book extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "isbn", length =20, unique = true, nullable = false)
    private String isbn;

    @Column(name = "publisher_name", nullable = false)
    private String publisherName;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(name = "author_address", nullable = false)
    private String authorAddress;

    @Column(name = "published_on", columnDefinition = "mediumint", nullable = false)
    @Convert(converter = YearMonthIntegerAttributeConverter.class)
    private YearMonth year;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "price", precision = 19, scale = 4, nullable = false)
    private Double price;

    @JsonBackReference("author")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @JsonBackReference("publisher")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;


    @JsonManagedReference("warehouseBook")
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "wrBook",
            orphanRemoval = true
    )
    private List<WarehouseBook> warehouseBook = new ArrayList<>();


    @JsonManagedReference("shoppingBasketBook")
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "shoppingBasketBook",
            orphanRemoval = true
    )
    private List<ShoppingBasketBook> shoppingBasketBooks = new ArrayList<>();

    public void addWarehouse(WarehouseBook item){
        this.warehouseBook.add(item);
        item.setWrBook(this);
    }
}
