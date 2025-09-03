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

import java.time.LocalDateTime;

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

    // RF 1.1: Registrar nuevas herramientas con datos básicos (nombre, categoría, estado
    // inicial, valor de reposición)
    public ToolEntity createTool(ToolEntity tool){
        ToolEntity savedTool = toolRepository.save(tool);

        KardexEntity kardex = new KardexEntity();
        kardex.setTool(savedTool);

        // Check if kardex type name exists in the DB
        KardexTypeEntity entryType = kardexTypeRepository.findByName("Ingreso");

        if (entryType == null) {
            throw new RuntimeException("Tipo de kardex 'Ingreso' no encontrado");
        }

        kardex.setType(entryType);
        kardex.setMovementDate(LocalDateTime.now());
        kardex.setQuantity(savedTool.getStock());

        kardexRepository.save(kardex);

        return savedTool;
    }
    // RF 1.2: Dar de baja herramientas dañadas o en desuso.
    public void deleteTool(ToolEntity tool){
        ToolStateEntity unserviceableState = toolStateRepository.findByName("Dada de baja");

        if (unserviceableState == null){
            throw new RuntimeException("Estado 'Dado de baja' no encontrado");
        }

        if (tool.getCurrentState().equals(unserviceableState)) {
            toolRepository.delete(tool);
        } else {
            throw new RuntimeException("La herramienta no está dada de baja, no se puede eliminar");
        }
    }
}
