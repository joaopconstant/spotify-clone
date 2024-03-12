package br.com.ibmec.cloud.spotifyclone.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class PlaylistMusica {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Playlist playlist;

    @ManyToOne
    private Musica musica;
}
