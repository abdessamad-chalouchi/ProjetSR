package com.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carnets")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JacksonXmlRootElement
@ToString
public class Carnet {
    @EmbeddedId
    private CarnetId id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_client",nullable = false,insertable=false, updatable=false)
    @ToString.Exclude
    private Client idClient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_sport", nullable = false,insertable=false, updatable=false)
    @ToString.Exclude
    @JsonIgnore
    private Sport idSport;

    @Column(name = "nombre_entrees", nullable = false)
    private Integer nombreEntrees;

    @JsonProperty("id")
    public String getSId() {
        return id.getIdClient() + "-" + id.getIdSport();
    }
    @JsonProperty("id")
    public void setSId(String sid) {
        String[] s = sid.split("-");
        if(s.length == 2){
            id.setIdClient(Integer.parseInt(s[0]));
            id.setIdSport(Integer.parseInt(s[1]));
        }
    }

    @JsonProperty("sportName")
    public String getSport() {
        return idSport.getNom();
    }

    @JsonProperty("sport")
    public int getSportId() {
        return idSport.getId();
    }

    @JsonProperty("username")
    public String getUserName() {
        return idClient.getEmail();
    }

    @JsonProperty("client")
    public int getUserId() {
        return idClient.getId();
    }

}