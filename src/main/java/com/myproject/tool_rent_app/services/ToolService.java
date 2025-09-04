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
    // inicial, valor de reposición) -> Actualizar el kardex
    public ToolEntity createTool(ToolEntity tool){
        String entryType = "Ingreso";

        KardexTypeEntity kardexType = kardexTypeRepository.findByName(entryType);

        // Validación de la existencia en la bd del tipo de kardex 'Ingreso'
        if (!kardexType.getName().equals(entryType)) {
            throw new RuntimeException("Tipo de kardex ' " + entryType + "' no encontrado");
        }

        ToolEntity savedTool = toolRepository.save(tool);

        KardexEntity kardex = new KardexEntity();
        kardex.setTool(savedTool);
        kardex.setType(kardexType);
        kardex.setMovementDate(LocalDateTime.now());
        kardex.setQuantity(savedTool.getStock());

        kardexRepository.save(kardex);

        return savedTool;
    }

    // RF 1.2: Dar de baja herramientas dañadas o en desuso. -> Actualizar el kardex
    public void deleteTool(ToolEntity tool){
        String toolState = "Dada de baja";

        ToolStateEntity toolStateEntity = toolStateRepository.findByName(toolState);

        // Validación de la existencia en la bd del estado de herramienta 'Dado de baja'
        if (!toolStateEntity.getName().equals(toolState)) {
            throw new RuntimeException("Estado '" + toolState + "' no encontrado");
        }

        // Validación de que la herramienta por eliminar tenga el estado 'Dada de baja'
        if (tool.getCurrentState().getName().equals(toolState)) {
            String entryType = "Baja";

            KardexTypeEntity kardexType = kardexTypeRepository.findByName(entryType);

            // Validación de la existencia en la bd del tipo de kardex 'Baja'
            if (!kardexType.getName().equals(entryType)) {
                throw new RuntimeException("Tipo de kardex '" +  entryType + "' no encontrado");
            }

            // Ingresa un nuevo movimiento en el kardex
            KardexEntity kardex = new KardexEntity();
            kardex.setTool(tool);
            kardex.setType(kardexType);
            kardex.setMovementDate(LocalDateTime.now());
            kardex.setQuantity(tool.getStock());

            kardexRepository.save(kardex);

            toolRepository.delete(tool);
        }
        throw new RuntimeException("La herramienta no está '" + toolState + "', no se puede eliminar");
    }
}
