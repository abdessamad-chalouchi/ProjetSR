package com.app.controllers;

import com.app.entities.Sport;
import com.app.services.SportServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/v1/public"},
        produces = {"application/json", "application/xml"},
        consumes = {"application/json", "application/xml", MediaType.ALL_VALUE})
public class PublicController {
    private final SportServices sportServices;
    @GetMapping("sports")
    public ResponseEntity<List<Sport>> getAllSports(){
        return ResponseEntity.ok(sportServices.getAllSports());
    }
}
