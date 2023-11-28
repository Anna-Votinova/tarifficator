package com.neoflex.product.entity;

import com.neoflex.product.entity.listeners.UserRevisionListener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;

@Getter
@Setter
@Entity
@RevisionEntity(UserRevisionListener.class)
@Table(name = "revision_info")
@NoArgsConstructor
public class Revision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    private Long id;

    @RevisionTimestamp
    @Column(name = "revision_timestamp")
    private Long timestamp;

    @Column(name = "revision_author")
    private String username;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Revision)) return false;
        return id != null && id.equals(((Revision) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
