package com.app.entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.io.Serializable;


@Data
@JacksonXmlRootElement
public class AddTerrainToSportRequest implements Serializable {
    private int Sport;
    private String Terrain;
}