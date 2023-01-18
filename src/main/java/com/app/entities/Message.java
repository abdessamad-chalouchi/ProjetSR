package com.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JacksonXmlRootElement
public class Message implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_ecriture", nullable = false)
    private Instant dateEcriture;

    @Column(name = "message", nullable = false, length = 45)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_client", nullable = false)
    private Client idClient;

    @JsonIgnore
    public Client getIdClient() {
        return idClient;
    }

    @JsonProperty("From")
    public String getFrom() {
        return idClient.getPrenom() + " " + idClient.getNom();
    }

    public Message(String msg,Client client) {
        this.dateEcriture = Instant.now();
        this.message = msg;
        this.idClient = client;
    }

}