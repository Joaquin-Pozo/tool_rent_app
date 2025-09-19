package com.myproject.tool_rent_app.controllers;


import com.myproject.tool_rent_app.entities.KardexEntity;
import com.myproject.tool_rent_app.services.KardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/kardex")
@CrossOrigin("*")
public class KardexController {
    @Autowired
    private KardexService kardexService;

    @GetMapping("/")
    public ResponseEntity<List<KardexEntity>> listKardexs(){
        List<KardexEntity> kardexs = kardexService.getKardexs();
        return ResponseEntity.ok(kardexs);
    }

    @GetMapping("/tool/{toolId}")
    public ResponseEntity<List<KardexEntity>> getMovementsByTool(@PathVariable Long toolId){
        List<KardexEntity> kardexMovements = kardexService.getMovementsByTool(toolId);
        return ResponseEntity.ok(kardexMovements);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<KardexEntity>> getMovementsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<KardexEntity> kardexMovements = kardexService.getMovementsByDateRange(start, end);
        return ResponseEntity.ok(kardexMovements);
    }
}
