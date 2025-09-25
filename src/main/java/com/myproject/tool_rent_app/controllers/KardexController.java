package com.myproject.tool_rent_app.controllers;


import com.myproject.tool_rent_app.entities.KardexEntity;
import com.myproject.tool_rent_app.services.KardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    // recibe las fechas de inicio y fin como yyyy-mm-dd
    @GetMapping("/date-range")
    public ResponseEntity<List<KardexEntity>> getMovementsByDateRange(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        List<KardexEntity> kardexMovements = kardexService.getMovementsByDateRange(start, end);
        return ResponseEntity.ok(kardexMovements);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<KardexEntity>> filter(
            @RequestParam(required = false) Long toolId,
            @RequestParam(required = false) LocalDate start,
            @RequestParam(required = false) LocalDate end) {
        if (toolId != null && start != null && end != null) {
            return ResponseEntity.ok(kardexService.getMovementsByToolAndDateRange(toolId, start, end));
        } else if (toolId != null) {
            return ResponseEntity.ok(kardexService.getMovementsByTool(toolId));
        } else if (start != null && end != null) {
            return ResponseEntity.ok(kardexService.getMovementsByDateRange(start, end));
        } else {
            return ResponseEntity.ok(kardexService.getKardexs());
        }
    }

}
