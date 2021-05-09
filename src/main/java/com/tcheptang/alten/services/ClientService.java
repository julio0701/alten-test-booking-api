package com.tcheptang.alten.services;

import com.tcheptang.alten.model.Client;

import java.util.Optional;

public interface ClientService {

    Client save(Client client);

    Optional<Client> findById(Long id);
}
