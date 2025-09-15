package com.myproject.tool_rent_app.controllers;

import com.myproject.tool_rent_app.entities.ToolEntity;
import com.myproject.tool_rent_app.repositories.ToolRepository;
import com.myproject.tool_rent_app.services.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tools")
public class ToolController {

    @Autowired
    private ToolService toolService;

    @Autowired
    private ToolRepository toolRepository;

    // Crea una nueva herramienta
    @PostMapping
    public ToolEntity createTool(@RequestBody ToolEntity tool) {
        return toolService.createTool(tool);
    }

    // Da de baja una herramienta
    @DeleteMapping("/{id}")
    public void deleteTool(@PathVariable Long id) {
        ToolEntity tool = toolRepository.findById(id).orElseThrow(() -> new RuntimeException("Herramienta no encontrada"));
        toolService.deleteTool(tool);
    }

    // Cambia el estado de una herramienta
    @PutMapping("/{id}/state")
    public void changeToolState(@PathVariable Long id, @RequestParam String newState) {
        ToolEntity tool = toolRepository.findById(id).orElseThrow(() -> new RuntimeException("Herramienta no encontrada"));
        toolService.changeToolState(tool, newState);
    }

    // Busca una herramienta por su nombre
    @GetMapping("/by-name")
    public ToolEntity getToolByName(@RequestParam String name) {
        return toolService.getToolByName(name);
    }
}
