package com.myproject.tool_rent_app.services;

import com.myproject.tool_rent_app.entities.KardexEntity;
import com.myproject.tool_rent_app.entities.KardexTypeEntity;
import com.myproject.tool_rent_app.entities.ToolEntity;
import com.myproject.tool_rent_app.repositories.KardexRepository;
import com.myproject.tool_rent_app.repositories.KardexTypeRepository;
import com.myproject.tool_rent_app.repositories.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ToolService {

    @Autowired
    ToolRepository toolRepository;

    @Autowired
    KardexRepository kardexRepository;

    @Autowired
    KardexTypeRepository kardexTypeRepository;

    public ToolEntity createTool(ToolEntity tool){
        ToolEntity savedTool = toolRepository.save(tool);

        KardexEntity kardex = new KardexEntity();
        kardex.setTool(savedTool);

        // Check if kardex type name exists in the DB
        KardexTypeEntity entryType = kardexTypeRepository.findByName("Ingreso")
                .orElseThrow(() -> new RuntimeException("Tipo de kardex no encontrado"));

        kardex.setType(entryType);
        kardex.setMovementDate(LocalDateTime.now());
        kardex.setQuantity(savedTool.getStock());

        return savedTool;
    }
}
