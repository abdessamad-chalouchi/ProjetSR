package com.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class CarnetUpdateRequest implements Serializable {
    private String id;
    private int nombreEntrees;
    @JsonIgnore
    public int getClient() {
        return Integer.valueOf(id.split("-")[0]);
    }
    @JsonIgnore
    public int getSport() {
        return Integer.valueOf(id.split("-")[1]);
    }
}