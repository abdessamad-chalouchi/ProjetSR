package com.app.controllers;

import com.app.entities.Carnet;
import com.app.entities.CarnetId;
import com.app.entities.CarnetUpdateRequest;
import com.app.services.CarnetService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = {"/api/v1/admin/carnet"},
        produces = {"application/json", "application/xml"},
        consumes = {"application/json", "application/xml", MediaType.ALL_VALUE})
public class CarnetController {

    private final CarnetService carnetService;
    @PostMapping
    public Carnet createCarnet(@RequestBody CarnetService.CarnetCreate carnet) {
        return carnetService.createCarnet(carnet);
    }
    @PutMapping
    public Carnet updateCarnet(@RequestBody CarnetUpdateRequest carnet) {
        return carnetService.updateCarnet(carnet);
    }
    @GetMapping(value={"/{idClient}-{idSport}","/{idClient}/{idSport}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Carnet> getCarnetById(@PathVariable Integer idClient, @PathVariable Integer idSport) {
        CarnetId id = new CarnetId(idClient, idSport);
        return carnetService.getCarnetById(id);
    }
    @GetMapping
    public ResponseEntity<List<Carnet>> getAllCarnets() {
        return ResponseEntity.ok(carnetService.getAllCarnets());
    }
    @DeleteMapping("/{id}")
    public void deleteCarnetById(@PathVariable String id) {
        String[] s = id.split("-");
        CarnetId c = new CarnetId(Integer.parseInt(s[0]),Integer.parseInt(s[1]));
        carnetService.deleteCarnetById(c);
    }
    @DeleteMapping("/all")
    public void deleteAllCarnets() {
        carnetService.deleteAllCarnets();
    }
    @PostMapping(value={"/{idClient}-{idSport}/buy","/{idClient}/{idSport}/buy"})
    public ResponseEntity<Carnet> buyTicket(@PathVariable Integer idClient, @PathVariable Integer idSport, @RequestBody Integer nombres) {
        Carnet carnet = carnetService.buyTicket(idClient, idSport, nombres);
        if(carnet != null) {
            return ResponseEntity.ok(carnet);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }
}
