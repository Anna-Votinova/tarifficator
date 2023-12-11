package com.neoflex.product.entity;

import com.neoflex.product.dto.enums.ProductType;
import com.neoflex.product.dto.TariffDto;
import com.neoflex.product.entity.listeners.OperationProductListener;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@ToString
@Builder
@Entity
@Audited
@Table(name = "product", schema = "public")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@EntityListeners(OperationProductListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @Column(nullable = false, updatable = false, unique = true)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "product_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String description;

    @Column(name = "tariff")
    @Type(type = "jsonb")
    private TariffDto tariffDto;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Version
    @Setter(AccessLevel.PRIVATE)
    private long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        return id != null && id.equals(((Product) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
