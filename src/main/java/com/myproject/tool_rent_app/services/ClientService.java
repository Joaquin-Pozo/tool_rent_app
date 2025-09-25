package com.myproject.tool_rent_app.services;

import com.myproject.tool_rent_app.entities.ClientEntity;
import com.myproject.tool_rent_app.entities.ClientStateEntity;
import com.myproject.tool_rent_app.repositories.ClientRepository;
import com.myproject.tool_rent_app.repositories.ClientStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientStateRepository clientStateRepository;

    // Actualiza el estado del cliente
    public ClientEntity changeClientState(Long id, String newState) {
        ClientEntity client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        ClientStateEntity clientState = clientStateRepository.findByName(newState);
        client.setCurrentState(clientState);
        return clientRepository.save(client);
    }

    public ArrayList<ClientEntity> getClients(){
        return (ArrayList<ClientEntity>) clientRepository.findAll();
    }

    public ClientEntity getClientById(Long id) {
        return clientRepository.findById(id).get();
    }

    public ClientEntity saveClient(ClientEntity client){
        return clientRepository.save(client);
    }

    public ClientEntity updateClient(ClientEntity client){
        return  clientRepository.save(client);
    }

    public boolean deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            return false;
        }
        clientRepository.deleteById(id);
        return true;
    }
}
