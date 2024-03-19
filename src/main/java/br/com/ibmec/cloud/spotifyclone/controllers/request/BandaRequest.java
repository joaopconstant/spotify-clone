package br.com.ibmec.cloud.spotifyclone.controllers.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BandaRequest {
    private UUID id;

    @NotBlank(message = "Campo nome é obrigatório")
    private String nome;

    private String descricao;

    private String imagem;

    private List<MusicaRequest> musicas;
}
