package com.app.entities;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@Data
@JacksonXmlRootElement
@AllArgsConstructor
@NoArgsConstructor
public class TerrainRequest {
    private String code;
    private String surface;
}
