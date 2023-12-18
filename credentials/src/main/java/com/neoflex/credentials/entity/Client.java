package com.neoflex.credentials.entity;

import com.neoflex.credentials.dto.enums.Role;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "client", schema = "public")
@Builder
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bank_id")
    private String bankId;

    private String lastname;

    private String firstname;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "birth_place")
    private String birthPlace;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "password_id")
    @ToString.Exclude
    private Password password;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.ROLE_USER;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_address")
    @ToString.Exclude
    private Address registrationAddress;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "residential_address")
    @ToString.Exclude
    private Address residentialAddress;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        return id != null && id.equals(((Client) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
