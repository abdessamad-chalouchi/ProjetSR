package com.app.entities;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement
public class SportRequest {

    private String nom;
    private Integer nombreJoueurs;
}
