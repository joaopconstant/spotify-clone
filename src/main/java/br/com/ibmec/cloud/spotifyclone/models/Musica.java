package br.com.ibmec.cloud.spotifyclone.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String nome;

    @Column
    private Integer duracao;

    @Column
    private String imagem;

    @ManyToOne()
    @JsonIgnore
    private Banda banda;
}
