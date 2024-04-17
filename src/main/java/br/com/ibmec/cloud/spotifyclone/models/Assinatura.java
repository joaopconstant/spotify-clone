package br.com.ibmec.cloud.spotifyclone.models;

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
    private Usuario usuario;

    @ManyToOne
    private Plano plano;

    @Column
    private Boolean ativo;
}
