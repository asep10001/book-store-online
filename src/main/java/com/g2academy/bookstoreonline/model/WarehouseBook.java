package com.g2academy.bookstoreonline.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "warehouse_book")
@Audited
@AuditTable("warehouse_book_audit")
public class WarehouseBook extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "warehouse_address", nullable = false)
    private String warehouseAdress;
    @Column(name = "book_ISBN", nullable = false)
    private String bookISBN;

    @JsonBackReference("warehouseBook")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_book_id")
    private Book wrBook;


    @JsonBackReference("warehouse")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private WareHouse warehouse;

//    @Column(name = "total_book", nullable = false)
//    private Integer count = 0;
//
//    public void addCounter() {
//        this.count++;
//    }
}
