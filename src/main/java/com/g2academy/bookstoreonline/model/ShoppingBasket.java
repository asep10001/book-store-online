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

@Entity
@Table(name = "shopping_basket")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Audited
@AuditTable("shopping_basket_audit")
public class ShoppingBasket extends BaseEntity<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long getShopBasId(){
        return id;
    }


    @Column(name = "user_email")
    private String email;


    @JsonBackReference(value = "customer")
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @JsonManagedReference("shoppingBasket")
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "shoppingBasket",
            orphanRemoval = true
    )
    private List<ShoppingBasketBook> shoppingBasketBooks = new ArrayList<>();

//    @JsonBackReference(value = "shoppingBasketBook")
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "shopping_basket_book_id")
//    private ShoppingBasketBook shoppingBasketBook;

}
