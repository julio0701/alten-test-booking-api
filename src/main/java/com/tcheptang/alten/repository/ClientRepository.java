package com.tcheptang.alten.repository;

import com.tcheptang.alten.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
