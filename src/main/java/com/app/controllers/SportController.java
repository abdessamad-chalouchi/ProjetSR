package com.app.controllers;

import com.app.entities.AddTerrainToSportRequest;
import com.app.entities.Sport;
import com.app.entities.SportRequest;
import com.app.entities.Terrain;
import com.app.services.SportServices;
import com.app.services.TerrainServices;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/v1/"},
        produces = {"application/json", "application/xml"},
        consumes = {"application/json", "application/xml", MediaType.ALL_VALUE})
public class SportController {
    private final SportServices sportServices;
    private final TerrainServices terrainServices;

    @GetMapping(value = {"client/sport"})
    public ResponseEntity<List<Sport>> getAllClients() {
        return ResponseEntity.ok(sportServices.getAllSports());
    }
    @GetMapping(value = {"client/sport/{id}"})
    public ResponseEntity<Sport> getSport(@PathVariable int id) {
        Optional<Sport> s = sportServices.getSport(id);
        if(s.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(s.get());
    }

    @PostMapping("admin/sport/save")
    public ResponseEntity<Sport> saveSport(@RequestBody SportRequest sportRequest){
        Sport sport = new Sport(sportRequest.getNom(),sportRequest.getNombreJoueurs());
        return ResponseEntity.ok(sportServices.save(sport));
    }
    @Data
    public static class SportData implements Serializable {
        private int id;
        private String nom;
        private int nombreJoueurs;
        private List<String> terrains = new ArrayList<>();
    }
    @PutMapping("admin/sport/update")
    public ResponseEntity<Sport> updateSport(@RequestBody SportData sport){
        Optional<Sport> s = sportServices.getSport(sport.getId());
        if(s.isEmpty())
            return ResponseEntity.badRequest().build();
        Sport sport2 = s.get();
        sport2.getTerrains().removeIf(t -> (!sport.getTerrains().contains(t.getCode())));
        Set<String> oldTerrains = sport2.getTerrainsList();
        for (String terrain : sport.getTerrains()) {
            if(!oldTerrains.contains(terrain)){
                Optional<Terrain> t = terrainServices.getTerrainByCode(terrain);
                if(t.isPresent()) sport2.getTerrains().add(t.get());
            }
        }
        sport2.setNom(sport.getNom());
        sport2.setNombreJoueurs(sport.getNombreJoueurs());
        return ResponseEntity.ok(sportServices.save(sport2));
    }

    @DeleteMapping("admin/sport/{id}")
    public ResponseEntity<Sport> updateSport(@PathVariable Integer id){
        return ResponseEntity.ok(sportServices.delete(id));
    }


    @PostMapping("admin/sport/addTerrainToSport")
    public ResponseEntity<?> addTerrainToSport(@RequestBody AddTerrainToSportRequest form) {
        sportServices.saveToTerrain(String.valueOf(form.getSport()),form.getTerrain());
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("admin/sport/removeTerrainToSport/{sport_id}")
    public ResponseEntity<?> removeTerrain(@PathVariable("sport_id") Integer sportId,
                                           @RequestParam String terrainCode){
        sportServices.removeTerrain(sportId,terrainCode);
        return ResponseEntity.ok().build();
    }
}
