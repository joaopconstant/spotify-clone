package br.com.ibmec.cloud.spotifyclone.controllers;

import br.com.ibmec.cloud.spotifyclone.controllers.request.LoginRequest;
import br.com.ibmec.cloud.spotifyclone.models.Playlist;
import br.com.ibmec.cloud.spotifyclone.models.Usuario;
import br.com.ibmec.cloud.spotifyclone.repository.PlaylistRepository;
import br.com.ibmec.cloud.spotifyclone.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PlaylistRepository playlistRepository;
    private final String DEFAULT_PLAYLIST = "Músicas Curtidas";

    @PostMapping
    public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario){

        // VALIDAR EMAIL NA BASE
        if(this.repository.findUsuarioByEmail(usuario.getEmail()).isEmpty() == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // CRIAR A LISTA DEFAULT DO USUARIO
        Playlist playlist = new Playlist();
        playlist.setNome(this.DEFAULT_PLAYLIST);
        playlist.setUsuario(usuario);

        usuario.getPlaylists().add(playlist);

        this.repository.save(usuario);
        this.playlistRepository.save(playlist);

        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Usuario> obter(@PathVariable("id")UUID id){
        return this.repository.findById(id).map(usuario -> {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/login")
    public ResponseEntity<Usuario> login(@Valid @RequestBody LoginRequest request){
        Optional<Usuario> usuarioOptional = this.repository.findUsuarioByEmailAndSenha(request.getEmail(), request.getSenha());

        return usuarioOptional.map(usuario -> {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
}
