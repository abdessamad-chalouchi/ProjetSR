package com.app.services;

import com.app.entities.Terrain;
import com.app.repository.TerrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TerrainServices {
    private final TerrainRepository terrainRepository;

    public List<Terrain> getAllTerrains() {
        return terrainRepository.findAll();
    }

    public Optional<Terrain> getTerrain(int id) {
        return terrainRepository.findById(id);
    }

    public Optional<Terrain> getTerrainByCode(String code) {
        return terrainRepository.findByCode(code);
    }

    public Terrain save(Terrain terrain) {
        if(terrainRepository.findByCode(terrain.getCode()).isPresent() && !Objects.equals(terrainRepository.findByCode(terrain.getCode()).get().getId(), terrain.getId())) {
            throw new IllegalStateException("Terrain with code "+terrain.getCode()+" already saved");
        }
        return terrainRepository.save(terrain);
    }
    public Terrain delete(Integer id) {

        if(!terrainRepository.findById(id).isPresent()) {
            throw new IllegalStateException("No Terrain Exist with id "+id);
        }
        Terrain terrain = terrainRepository.findById(id).get();
        terrainRepository.delete(terrain);
        return terrain;
    }
}
