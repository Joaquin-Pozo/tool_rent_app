package com.myproject.tool_rent_app.controllers;

import com.myproject.tool_rent_app.entities.ClientEntity;
import com.myproject.tool_rent_app.repositories.ClientRepository;
import com.myproject.tool_rent_app.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@CrossOrigin("*")
public class ClientController {

    @Autowired
    private ClientService clientService;


    // Muestra todos los clientes
    @GetMapping("/")
    public ResponseEntity<List<ClientEntity>> listClients(){
        List<ClientEntity> clients = clientService.getClients();
        return ResponseEntity.ok(clients);
    }

    // Obtiene un cliente por id
    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> getClientById(@PathVariable Long id){
        ClientEntity clientEntity = clientService.getClientById(id);
        return ResponseEntity.ok(clientEntity);
    }

    // Crea un nuevo cliente
    @PostMapping("/")
    public ResponseEntity<ClientEntity> createClient(@RequestBody ClientEntity client){
        ClientEntity newClient = clientService.saveClient(client);
        return ResponseEntity.ok(newClient);
    }

    // Actualiza a un cliente
    @PutMapping("/")
    public ResponseEntity<ClientEntity> updateClient(@RequestBody ClientEntity client){
        ClientEntity clientUpdated = clientService.updateClient(client);
        return ResponseEntity.ok(clientUpdated);
    }

    // Elimina a un cliente por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteClientById(@PathVariable Long id) {
        var isDeleted = clientService.deleteClient(id);
        return ResponseEntity.ok(isDeleted);
    }

    // Cambia el estado de un cliente
    @PutMapping("/{id}/state")
    public ResponseEntity<ClientEntity> updateClientState(@PathVariable Long id, @RequestParam String newState) {
        ClientEntity updatedClient = clientService.changeClientState(id, newState);
        return ResponseEntity.ok(updatedClient);
    }
}
