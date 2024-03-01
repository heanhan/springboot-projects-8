package com.example.ddd.domain.model;

import com.google.common.base.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Table(name = "course_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_no", length = 32, nullable = false, unique = true)
    private String itemNo;

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "remark", length = 256)
    private String remark;

    @Column(name = "study_type", nullable = false)
    private Integer studyType;

    @Column(name = "period")
    private Integer period;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deadline")
    private Date deadline;

    public static CourseItem of(String itemNo, String name, Integer categoryId, BigDecimal price, String remark, Integer studyType,
                                Integer period, Date deadline) {
        return new CourseItem(null, itemNo, name, categoryId, price, remark, studyType, period, deadline);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseItem that = (CourseItem) o;
        return Objects.equal(itemNo, that.itemNo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(itemNo);
    }

}
