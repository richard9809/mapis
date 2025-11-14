package com.personal.mapis.controllers;

import com.personal.mapis.models.dto.HealthCallbackDTO;
import com.personal.mapis.services.CallbackProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/callbacks/health")
@RequiredArgsConstructor
public class HealthCallbackController {

    private final CallbackProcessingService callbackProcessingService;

    @PostMapping("/approvals")
    public ResponseEntity<Map<String, Object>> handleHealthApprovalCallback(@RequestBody HealthCallbackDTO body) {
        callbackProcessingService.handleHealthCallback(body);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Health approval update received",
                        "approvalId", body.getApprovalId(),
                        "status", body.getStatus()
                )
        );
    }
}
