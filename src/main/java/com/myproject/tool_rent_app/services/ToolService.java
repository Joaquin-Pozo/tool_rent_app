package com.myproject.tool_rent_app.services;

import com.myproject.tool_rent_app.entities.KardexEntity;
import com.myproject.tool_rent_app.entities.KardexTypeEntity;
import com.myproject.tool_rent_app.entities.ToolEntity;
import com.myproject.tool_rent_app.entities.ToolStateEntity;
import com.myproject.tool_rent_app.repositories.KardexRepository;
import com.myproject.tool_rent_app.repositories.KardexTypeRepository;
import com.myproject.tool_rent_app.repositories.ToolRepository;
import com.myproject.tool_rent_app.repositories.ToolStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class ToolService {

    @Autowired
    ToolRepository toolRepository;

    @Autowired
    ToolStateRepository toolStateRepository;

    @Autowired
    KardexRepository kardexRepository;

    @Autowired
    KardexTypeRepository kardexTypeRepository;

    public ArrayList<ToolEntity> getTools() {
        return (ArrayList<ToolEntity>) toolRepository.findAll();
    }

    public ToolEntity getToolById(Long id) {
        return toolRepository.findById(id).get();
    }

    public ToolEntity updateTool(ToolEntity tool) {
        return toolRepository.save(tool);
    }

    // RF 1.1: Registrar nuevas herramientas con datos básicos (nombre, categoría, estado
    // inicial, valor de reposición) -> Actualizar el kardex
    public ToolEntity createTool(ToolEntity tool){
        // Agrega la nueva herramienta
        ToolEntity savedTool = toolRepository.save(tool);

        // Ingresa un nuevo movimiento en el kardex
        KardexTypeEntity kardexType = kardexTypeRepository.findByName("Ingreso");
        KardexEntity newKardex = new KardexEntity();
        newKardex.setTool(savedTool);
        newKardex.setType(kardexType);
        newKardex.setMovementDate(LocalDate.now());
        newKardex.setQuantity(savedTool.getStock());
        kardexRepository.save(newKardex);

        return savedTool;
    }
    // Modifica el estado actual de una herramienta
    public ToolEntity changeToolState(Long id, String newState) {
        ToolEntity tool = toolRepository.findById(id).orElseThrow(() -> new RuntimeException("Herramienta no encontrada"));
        ToolStateEntity toolState = toolStateRepository.findByName(newState);

        // Valida si el nuevo estado de la herramienta es 'Dada de baja'
        if (newState.equals("Dada de baja")) {
            KardexTypeEntity kardexType = kardexTypeRepository.findByName("Baja");

            // Ingresa un nuevo movimiento en el kardex
            KardexEntity newKardex = new KardexEntity();
            newKardex.setTool(tool);
            newKardex.setType(kardexType);
            newKardex.setMovementDate(LocalDate.now());
            newKardex.setQuantity(tool.getStock());
            kardexRepository.save(newKardex);

            tool.setStock(0);
        }

        tool.setCurrentState(toolState);
        return toolRepository.save(tool);
    }

    // Obtiene una herramienta por su nombre
    public ToolEntity getToolByName(String name){
        return toolRepository.findByName(name);
    }
}
