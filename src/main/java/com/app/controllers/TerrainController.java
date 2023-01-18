package com.app.controllers;

import com.app.entities.Sport;
import com.app.entities.Terrain;
import com.app.entities.TerrainData;
import com.app.entities.TerrainRequest;
import com.app.services.SportServices;
import com.app.services.TerrainServices;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@RequestMapping(value = {"/api/v1"},
        produces = {"application/json", "application/xml"},
        consumes = {"application/json", "application/xml", MediaType.ALL_VALUE})
public class TerrainController {
    private final SportServices sportServices;
    private final TerrainServices terrainServices;

    @GetMapping(value = {"client/terrain"})
    public ResponseEntity<List<Terrain>> getAllClients() {
        return ResponseEntity.ok(terrainServices.getAllTerrains());
    }

    @GetMapping(value = {"client/terrain/{id}"})
    public ResponseEntity<Terrain> getTerrain(@PathVariable int id) {
        Optional<Terrain> s = terrainServices.getTerrain(id);
        if(s.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(s.get());
    }

    @PostMapping("admin/terrain/save")
    public ResponseEntity<Terrain> saveSport(@RequestBody TerrainRequest terrainRequest){
        Terrain terrain = new Terrain(terrainRequest.getCode(),terrainRequest.getSurface());
        return ResponseEntity.ok(terrainServices.save(terrain));
    }

    @PutMapping("admin/terrain/update")
    public ResponseEntity<Terrain> updateTerrain(@RequestBody TerrainData terrain){
        Optional<Terrain> t = terrainServices.getTerrain(terrain.getId());
        if(t.isEmpty())
            return ResponseEntity.badRequest().build();
        Terrain terrain2 = t.get();
        for(Sport sport : terrain2.getSports()){
            if(!terrain.getSports().contains(sport.getId()))
                sportServices.removeTerrain(sport.getId(), terrain.getCode());
        }
        Set<Integer> oldSports = terrain2.getSportsIDsList();
        for (Integer sport : terrain.getSports()) {
            if(!oldSports.contains(sport)){
                sportServices.saveToTerrain(String.valueOf(sport), terrain.getCode());
            }
        }
        terrain2.setCode(terrain.getCode());
        terrain2.setSurface(terrain.getSurface());
        return ResponseEntity.ok(terrainServices.save(terrain2));
    }
    @DeleteMapping("admin/terrain/{id}")
    public ResponseEntity<Terrain> updateSport(@PathVariable Integer id){
        return ResponseEntity.ok(terrainServices.delete(id));
    }
}
