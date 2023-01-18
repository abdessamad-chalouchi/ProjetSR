package com.app.services;

import com.app.entities.Sport;
import com.app.entities.Terrain;
import com.app.repository.SportRepository;
import com.app.repository.TerrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SportServices {
    private final SportRepository sportRepository;
    private final TerrainRepository terrainRepository;

    public List<Sport> getAllSports() {
        return sportRepository.findAll();
    }

    public Optional<Sport> getSport(int id) {
        return sportRepository.findById(id);
    }

    public Sport save(Sport sport) {
        return sportRepository.save(sport);
    }

    public Sport delete(Integer id) {
        if(sportRepository.findById(id).isEmpty()) {
            throw new IllegalStateException("No Sport Exist with id "+id);
        }
        Sport sport = sportRepository.findById(id).get();
        sportRepository.delete(sport);
        return sport;
    }


    public void saveToTerrain(String sportId, String terrainCode) {
        Optional<Sport> sport = sportRepository.findById(Integer.valueOf(sportId));
        Optional<Terrain> terrain = terrainRepository.findByCode(terrainCode);
        if(sport.isEmpty() || terrain.isEmpty()){
            throw new IllegalStateException("Please Enter a valid Sport and Terrain!!");
        }
        if(sport.get().getTerrains().contains(terrain.get())){
            throw new IllegalStateException("Terrain already added to this sport!!");
        }
        sport.get().getTerrains().add(terrain.get());
    }

    public void removeTerrain(Integer sportId, String terrainCode) {
        Optional<Sport> sport = sportRepository.findById(Integer.valueOf(sportId));
        Optional<Terrain> terrain = terrainRepository.findByCode(terrainCode);
        if(sport.isEmpty() || terrain.isEmpty()){
            throw new IllegalStateException("Please Enter a valid Sport and Terrain!!");
        }
        if(!sport.get().getTerrains().contains(terrain.get())){
            throw new IllegalStateException("No Terrain with code "+terrainCode+" associate to this sport!!");
        }
        sport.get().getTerrains().remove(terrain.get());
    }
}
