package com.myproject.tool_rent_app.controllers;

import com.myproject.tool_rent_app.entities.ClientEntity;
import com.myproject.tool_rent_app.repositories.ClientRepository;
import com.myproject.tool_rent_app.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    // Cambia el estado de un cliente
    @PutMapping("/{id}/state")
    public void changeClientState(@PathVariable Long id, @RequestParam String newState) {
        ClientEntity client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        clientService.changeClientState(client, newState);
    }
}
