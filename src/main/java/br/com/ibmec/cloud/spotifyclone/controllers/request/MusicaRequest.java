package br.com.ibmec.cloud.spotifyclone.controllers.request;

import lombok.Data;

import java.util.UUID;

@Data
public class MusicaRequest {
    private UUID id;
    private String nome;
    private Integer duracao;
}
