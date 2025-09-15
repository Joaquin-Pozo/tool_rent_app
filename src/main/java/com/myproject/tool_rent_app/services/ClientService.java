package com.myproject.tool_rent_app.services;

import com.myproject.tool_rent_app.entities.ClientEntity;
import com.myproject.tool_rent_app.entities.ClientStateEntity;
import com.myproject.tool_rent_app.repositories.ClientRepository;
import com.myproject.tool_rent_app.repositories.ClientStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientStateRepository clientStateRepository;

    // Actualiza el estado del cliente
    public void changeClientState(ClientEntity client, String newState) {
        ClientStateEntity newClientState = clientStateRepository.findByName(newState);
        client.setCurrentState(newClientState);
        clientRepository.save(client);
    }
}
