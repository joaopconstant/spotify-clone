package br.com.ibmec.cloud.spotifyclone.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Assinatura {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JsonIgnore
    private Usuario usuario;

    @ManyToOne
    private Plano plano;

    @Column
    private Boolean ativo;
}
