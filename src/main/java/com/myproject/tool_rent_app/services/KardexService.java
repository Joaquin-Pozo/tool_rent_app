package com.myproject.tool_rent_app.services;


import com.myproject.tool_rent_app.entities.KardexEntity;
import com.myproject.tool_rent_app.repositories.KardexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class KardexService {

    @Autowired
    private KardexRepository kardexRepository;

    public List<KardexEntity> getMovementsByTool(Long toolId){
        return kardexRepository.findByToolId(toolId);
    }

    public List<KardexEntity> getMovementsByDateRange(LocalDate startDate, LocalDate endDate){
        return kardexRepository.findByMovementDateBetween(startDate, endDate);
    }

    public List<KardexEntity> getMovementsByToolAndDateRange(Long toolId, LocalDate startDate, LocalDate endDate){
        return kardexRepository.findByToolIdAndMovementDateBetween(toolId, startDate, endDate);
    }

    public ArrayList<KardexEntity> getKardexs() {
        return (ArrayList<KardexEntity>) kardexRepository.findAll();
    }

}
