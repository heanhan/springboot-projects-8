package com.example.ddd.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 产品和明细是多对多的关系
 * 注意，ProductCourseItem是一个值对象，值对象是不能被修改的。所以这个类只提供了getter，并没有提供setter。
 */

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductCourseItem implements Serializable {

    @Column(name = "course_item_no", length = 32, nullable = false)
    private String courseItemNo;

    @Column(name = "new_price", precision = 10, scale = 2)
    private BigDecimal newPrice;

    public static ProductCourseItem of(String courseItemNo, BigDecimal retakePrice) {
        return new ProductCourseItem(courseItemNo, retakePrice);
    }

}
