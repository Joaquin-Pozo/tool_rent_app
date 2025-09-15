package com.myproject.tool_rent_app.services;


import com.myproject.tool_rent_app.entities.KardexEntity;
import com.myproject.tool_rent_app.repositories.KardexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class KardexService {

    @Autowired
    private KardexRepository kardexRepository;

    public List<KardexEntity> getMovementsByTool(Long toolId){
        return kardexRepository.findByToolId(toolId);
    }

    public List<KardexEntity> getMovementsByDateRange(LocalDateTime start, LocalDateTime end){
        return kardexRepository.findByMovementDateBetween(start, end);
    }




}
