package com.neoflex.tariffs.entity;

import com.neoflex.tariffs.entity.listeners.OperationTariffListener;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
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
@Table(name = "tariff", schema = "public")
@EntityListeners(OperationTariffListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Tariff {

    @Id
    @Column(nullable = false, updatable = false, unique = true)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double rate;

    @Column(name = "author_id", nullable = false)
    private Long author;

    @Version
    @Setter(AccessLevel.PRIVATE)
    private long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tariff)) return false;
        return id != null && id.equals(((Tariff) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
