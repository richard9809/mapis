package com.personal.mapis.controllers;

import com.personal.mapis.models.dto.PermitDetailsDTO;
import com.personal.mapis.models.dto.PermitRequestDTO;
import com.personal.mapis.models.dto.PermitResponseDTO;
import com.personal.mapis.services.PermitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permits")
@RequiredArgsConstructor
public class PermitController {
    private final PermitService permitService;

    @PostMapping
    public ResponseEntity<PermitResponseDTO> createPermit(@RequestBody PermitRequestDTO request) {
        PermitResponseDTO response = permitService.createPermit(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{permitNumber}")
    public ResponseEntity<PermitResponseDTO> getPermit(@PathVariable String permitNumber) {
        PermitResponseDTO response = permitService.getPermitByNumber(permitNumber);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/{permitNumber}/details")
    public ResponseEntity<PermitDetailsDTO> getPermitDetails(@PathVariable String permitNumber) {
        PermitDetailsDTO details = permitService.getPermitDetails(permitNumber);
        return ResponseEntity.ok(details);
    }
}
