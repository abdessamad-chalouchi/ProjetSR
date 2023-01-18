package com.app.entities;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
/**
 * InnerTerrainController
 */
public class TerrainData implements Serializable {
    private int id;
    private String code;
    private String surface;
    private List<Integer> sports = new ArrayList<>();
}
