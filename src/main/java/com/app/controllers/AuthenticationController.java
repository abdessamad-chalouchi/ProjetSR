package com.app.controllers;

import com.app.entities.Client;
import com.app.entities.auth.AuthenticationRequest;
import com.app.entities.auth.AuthenticationResponse;
import com.app.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = {"/api/v1/auth"},
        produces = {"application/json", "application/xml"},
        consumes = {"application/json", "application/xml", MediaType.ALL_VALUE})
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register/{isClient}")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody Client client,
            @PathVariable boolean isClient
    ) {
        return ResponseEntity.ok(service.register(client, isClient));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/currentUser")
    public ResponseEntity<Client> current(Authentication authentication) {
        if(authentication == null){
            throw new IllegalStateException("You are not connected!!");
        }
        return ResponseEntity.ok((Client) authentication.getPrincipal());
    }
}
