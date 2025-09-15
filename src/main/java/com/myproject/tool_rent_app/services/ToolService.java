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
        // Agrega la nueva herramienta
        ToolEntity savedTool = toolRepository.save(tool);

        // Ingresa un nuevo movimiento en el kardex
        KardexTypeEntity kardexType = kardexTypeRepository.findByName("Ingreso");
        KardexEntity newKardex = new KardexEntity();
        newKardex.setTool(savedTool);
        newKardex.setType(kardexType);
        newKardex.setMovementDate(LocalDateTime.now());
        newKardex.setQuantity(savedTool.getStock());
        kardexRepository.save(newKardex);

        return savedTool;
    }

    // RF 1.2: Dar de baja herramientas dañadas o en desuso. -> Actualizar el kardex
    public void deleteTool(ToolEntity tool){
        String toolState = "Dada de baja";

        // Validación de que la herramienta por eliminar tenga el estado 'Dada de baja'
        if (!tool.getCurrentState().getName().equals(toolState)) {
            throw new RuntimeException("La herramienta no está '" + toolState + "', no se puede eliminar");
        }

        KardexTypeEntity kardexType = kardexTypeRepository.findByName("Baja");

        // Ingresa un nuevo movimiento en el kardex
        KardexEntity newKardex = new KardexEntity();
        newKardex.setTool(tool);
        newKardex.setType(kardexType);
        newKardex.setMovementDate(LocalDateTime.now());
        newKardex.setQuantity(tool.getStock());
        kardexRepository.save(newKardex);

        // Elimina la herramienta (Dada de baja)
        toolRepository.delete(tool);
    }
    // Modifica el estado actual de una herramienta
    public void changeToolState(ToolEntity tool, String newState) {
        ToolStateEntity toolState = toolStateRepository.findByName(newState);
        tool.setCurrentState(toolState);
        toolRepository.save(tool);
    }

    // Obtiene una herramienta especifica
    public ToolEntity getToolByName(String name){
        return toolRepository.findByName(name);
    }
}
