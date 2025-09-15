package com.myproject.tool_rent_app.controllers;


import com.myproject.tool_rent_app.entities.KardexEntity;
import com.myproject.tool_rent_app.services.KardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/kardex")
public class KardexController {
    @Autowired
    private KardexService kardexService;

    @GetMapping("/tool/{toolId}")
    public List<KardexEntity> getMovementsByTool(@PathVariable Long toolId){
        return kardexService.getMovementsByTool(toolId);
    }

    @GetMapping("/date-range")
    public List<KardexEntity> getMovementsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return kardexService.getMovementsByDateRange(start, end);
    }
}
