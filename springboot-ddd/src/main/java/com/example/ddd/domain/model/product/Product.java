package com.example.ddd.domain.model.product;

import com.example.ddd.domain.ProductCourseItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 商品聚合根
 */
@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_no", length = 32, nullable = false, unique = true)
    private String productNo;

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "product_status", nullable = false)
    private Integer productStatus;

    @Column(name = "remark", length = 256)
    private String remark;

    @Column(name = "allow_across_category", nullable = false)
    private Boolean allowAcrossCategory;


    @ElementCollection(targetClass = ProductCourseItem.class)
    @CollectionTable(
            name = "product_course_item",
            uniqueConstraints = @UniqueConstraint(columnNames = {"product_no", "course_item_no"}),
            joinColumns = {@JoinColumn(name = "product_no", referencedColumnName = "product_no")}
    )
    private Set<ProductCourseItem> productCourseItems = new HashSet<>();


    public static Product of(String productNo, String name, BigDecimal price, Integer categoryId, Integer productStatus, String remark,
                             Boolean allowAcrossCategory, Set<ProductCourseItem> productCourseItems) {
        return new Product(null, productNo, name, price, categoryId, productStatus, remark, allowAcrossCategory, productCourseItems);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productNo, product.productNo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productNo);
    }
}
