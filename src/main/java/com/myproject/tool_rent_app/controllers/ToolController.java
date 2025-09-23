package com.myproject.tool_rent_app.controllers;

import com.myproject.tool_rent_app.entities.ToolEntity;
import com.myproject.tool_rent_app.services.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tools")
@CrossOrigin("*")
public class ToolController {

    @Autowired
    private ToolService toolService;

    // Muestra todas las herramientas
    @GetMapping("/")
    public ResponseEntity<List<ToolEntity>> listTools() {
        return ResponseEntity.ok(toolService.getTools());
    }

    // Obtiene una herramienta por id
    @GetMapping("/{id}")
    public ResponseEntity<ToolEntity> getToolById(@PathVariable Long id) {
        ToolEntity tool = toolService.getToolById(id);
        return ResponseEntity.ok(tool);
    }

    // Crea una nueva herramienta
    @PostMapping("/")
    public ResponseEntity<ToolEntity> createTool(@RequestBody ToolEntity tool) {
        ToolEntity newTool = toolService.createTool(tool);
        return ResponseEntity.ok(newTool);
    }

    // Actualiza una herramienta (stock, nombre, etc.)
    @PutMapping("/")
    public ResponseEntity<ToolEntity> updateTool(@RequestBody ToolEntity tool) {
        ToolEntity updated = toolService.updateTool(tool);
        return ResponseEntity.ok(updated);
    }

    // Cambia el estado de una herramienta
    @PutMapping("/{id}/state")
    public ResponseEntity<ToolEntity> updateToolState(@PathVariable Long id, @RequestParam String newState) {
        ToolEntity updatedTool = toolService.changeToolState(id, newState);
        return ResponseEntity.ok(updatedTool);
    }

    // Busca una herramienta por su nombre
    @GetMapping("/by-name")
    public ResponseEntity<ToolEntity> getToolByName(@RequestParam String name) {
        ToolEntity tool = toolService.getToolByName(name);
        return ResponseEntity.ok(tool);
    }
}
