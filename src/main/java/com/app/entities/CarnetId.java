package com.app.entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
@JacksonXmlRootElement
public class CarnetId implements Serializable {
    @Serial
    private static final long serialVersionUID = 6934162338657067693L;
    @Column(name = "id_client", nullable = false)
    private Integer idClient;

    @Column(name = "id_sport", nullable = false)
    private Integer idSport;
}
