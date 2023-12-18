package com.neoflex.credentials.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "password", schema = "public")
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "creation_date", nullable = false)
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime creationDate;

    @Column(name = "update_date", nullable = false)
    @UpdateTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime updateDate;

    public Password(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Password)) return false;
        return id != null && id.equals(((Password) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
