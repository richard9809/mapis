package com.personal.mapis.controllers;

import com.personal.mapis.models.dto.FireCallbackDTO;
import com.personal.mapis.services.CallbackProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/callbacks/fire")
@RequiredArgsConstructor
public class FireCallbackController {

    private final CallbackProcessingService callbackProcessingService;

    @PostMapping("/clearances")
    public ResponseEntity<Map<String, Object>> handleFireClearanceCallback(@RequestBody FireCallbackDTO body) {
        callbackProcessingService.handleFireCallback(body);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Fire clearance update received",
                        "clearanceId", body.getClearanceId(),
                        "status", body.getStatus()
                )
        );
    }
}
