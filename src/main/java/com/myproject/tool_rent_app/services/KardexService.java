package com.myproject.tool_rent_app.services;


import com.myproject.tool_rent_app.entities.KardexEntity;
import com.myproject.tool_rent_app.repositories.KardexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);
        return kardexRepository.findByMovementDateBetween(start, end);
    }

    public List<KardexEntity> getMovementsByToolAndDateRange(Long toolId, LocalDate startDate, LocalDate endDate){
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);
        return kardexRepository.findByToolIdAndMovementDateBetween(toolId, start, end);
    }

    public ArrayList<KardexEntity> getKardexs() {
        return (ArrayList<KardexEntity>) kardexRepository.findAll();
    }

}
