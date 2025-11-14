package com.personal.mapis.controllers;

import com.personal.mapis.models.dto.EnvCallbackDTO;
import com.personal.mapis.services.CallbackProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/callbacks/env")
@RequiredArgsConstructor
public class EnvCallbackController {

    private final CallbackProcessingService  callbackProcessingService;

    @PostMapping("/inspections")
    public ResponseEntity<Map<String, Object>> handleEnvInspectionCallback(@RequestBody EnvCallbackDTO body) {
        callbackProcessingService.handleEnvCallback(body);
        return ResponseEntity.ok(
                Map.of(
                        "message", "Environment inspection update received",
                        "inspectionId", body.getInspectionId(),
                        "status", body.getStatus()
                )
        );
    }
}
