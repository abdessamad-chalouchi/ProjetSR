package com.app.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entities.Client;
import com.app.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = {"/api/v1/admin"},
        produces = {"application/json", "application/xml"},
        consumes = {"application/json", "application/xml", MediaType.ALL_VALUE})
@RequiredArgsConstructor
public class AdminController {
    private final AuthenticationService aService;
    @GetMapping(value = {"/users"})
    public List<Client> getUsers() {
        return aService.getUsers();
    }
    @GetMapping(value = {"/users/{id}"})
    public ResponseEntity<Client> getUser(@PathVariable int id) {
        Optional<Client> u = aService.getUser(id);
        if(u.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(u.get());
    }
}
