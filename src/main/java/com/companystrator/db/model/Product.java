package com.companystrator.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "code")
    private Long code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price_COP")
    private double priceCop;

    @Column(name = "price_USD")
    private double priceUsd;

    @Column(name = "price_MXN")
    private double priceMxn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_category", referencedColumnName = "id", nullable = false)
    private ProductCategory primaryCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secondary_category", referencedColumnName = "id")
    private ProductCategory secondaryCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "NIT", nullable = false)
    private Company company;

}
