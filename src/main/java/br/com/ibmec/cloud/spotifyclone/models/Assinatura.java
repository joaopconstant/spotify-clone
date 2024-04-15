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
    @JoinColumn(name = "assinatura_id", referencedColumnName = "id")
    private Plano plano;

    @OneToOne
    private Usuario usuario;

    @Column
    private Boolean ativo;
}
