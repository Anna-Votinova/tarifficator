package com.neoflex.credentials.repository;

import com.neoflex.credentials.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {

    @Query("select c from Client c where lower(c.email) like lower(concat('%', :login,'%')) " +
            "or lower(c.bankId) like lower(concat('%', :login,'%'))" +
            "or lower(c.phoneNumber) like lower(concat('%', :login,'%'))")
    Optional<Client> findByLogin(@Param("login") String login);
}
