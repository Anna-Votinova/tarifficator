package com.neoflex.credentials.repository;

import com.neoflex.credentials.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
