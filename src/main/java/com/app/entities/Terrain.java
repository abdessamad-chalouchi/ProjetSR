package com.app.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "terrains")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JacksonXmlRootElement
public class Terrain {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "surface", nullable = false, length = 45)
    private String surface;

//    @JsonIgnore
    @ManyToMany(mappedBy = "terrains",fetch = FetchType.EAGER)
    private Set<Sport> sports = new HashSet<>();

    public Terrain(String code, String surface) {
        this.code = code;
        this.surface = surface;
    }

    @JsonProperty("sportsNames")
    public Set<String> getSportsList() {
        Set<String> ret = new HashSet<>();
        for (Sport sport : sports) {
            ret.add(sport.getNom());
        }
        return ret;
    }

    @JsonProperty("sports")
    public Set<Integer> getSportsIDsList() {
        Set<Integer> ret = new HashSet<>();
        for (Sport sport : sports) {
            ret.add(sport.getId());
        }
        return ret;
    }
}