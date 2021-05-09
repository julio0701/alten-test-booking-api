package com.tcheptang.alten.services.impl;

import com.tcheptang.alten.model.Client;
import com.tcheptang.alten.repository.ClientRepository;
import com.tcheptang.alten.repository.ReservationRepository;
import com.tcheptang.alten.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public Client save(Client client) {
        return this.clientRepository.save(client);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return this.clientRepository.findById(id);
    }
}
