package br.com.ibmec.cloud.spotifyclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String nome;

    @Column
    private Double preco;

    @OneToMany
    @JoinColumn(name = "plano_id", referencedColumnName = "id")
    private List<Assinatura> assinaturas;
}
