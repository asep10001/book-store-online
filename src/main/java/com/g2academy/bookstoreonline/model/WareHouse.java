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
@Table(name = "warehous")
@Audited
@AuditTable("warehouse_audit")
public class WareHouse extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "adress", nullable = false)
    private String address;

    @JsonManagedReference("warehouse")
    @OneToMany(
            targetEntity = WarehouseBook.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "warehouse",
            orphanRemoval = true
    )
    private List<WarehouseBook> wrBooks = new ArrayList<>();

    public void addWrbooks(WarehouseBook item){
        this.wrBooks.add(item);
        item.setWarehouse(this);
    }
}
