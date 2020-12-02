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
@Table(name = "shopping_basket_book")
@Audited
@AuditTable("shopping_basket_book_audit")
public class ShoppingBasketBook  extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "book_isbn", nullable = false)
    private String bookISBN;

    @JsonBackReference("shoppingBasketBook")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_basket_book_id")
    private Book shoppingBasketBook;

    @JsonBackReference("shoppingBasket")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_basket_id")
    private ShoppingBasket shoppingBasket;


}
