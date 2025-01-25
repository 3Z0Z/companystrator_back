package com.companystrator.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_category")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy = "primaryCategory", fetch = FetchType.LAZY)
    private List<Product> productsByPrimaryCat;

    @OneToMany(mappedBy = "secondaryCategory", fetch = FetchType.LAZY)
    private List<Product> productsBySecondatyCat;

}
