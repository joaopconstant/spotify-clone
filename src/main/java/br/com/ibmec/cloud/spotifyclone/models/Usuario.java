package br.com.ibmec.cloud.spotifyclone.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Usuario {

    public Usuario() {
        this.playlists = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @NotBlank(message = "Campo nome é obrigatório")
    private String nome;

    @Column
    @NotBlank(message = "Campo email é obrigatório")
    @Email(message = "Insira um email válido")
    private String email;

    @Column
    @NotBlank(message = "Campo senha é obrigatório")
    private String senha;

    @OneToMany
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Assinatura> assinaturas;

    @OneToMany
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Playlist> playlists;
}
