package br.com.ibmec.cloud.spotifyclone.controllers.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.UUID;

@Data
public class UsuarioRequest {
    @NotBlank(message = "Campo nome é obrigatório")
    private String nome;

    @NotBlank(message = "Campo email é obrigatório")
    @Email(message = "Insira um email válido")
    private String Email;

    @NotBlank(message = "Campo senha é obrigatório")
    private String senha;

    private UUID planoId;
}
