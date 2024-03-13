package br.com.ibmec.cloud.spotifyclone.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Banda {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String nome;

    @Column
    private String descricao;

    @Column
    private String imagem;

    @OneToMany
    @JoinColumn(name= "musica_id", referencedColumnName = "id")
    private List<Musica> musicas;
}
