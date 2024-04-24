package br.com.ibmec.cloud.spotifyclone.controllers;

import br.com.ibmec.cloud.spotifyclone.controllers.request.LoginRequest;
import br.com.ibmec.cloud.spotifyclone.controllers.request.PlaylistRequest;
import br.com.ibmec.cloud.spotifyclone.models.*;
import br.com.ibmec.cloud.spotifyclone.repository.MusicaRepository;
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

    @Autowired
    private MusicaRepository musicaRepository;
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

    @PostMapping("{id}/favoritar/{idMusica}")
    public ResponseEntity favoritar(@PathVariable("id")UUID id, @PathVariable("idMusica")UUID idMusica) {

        // Faço as buscar do usuario e musica
        Optional<Usuario> optionalUsuario = this.repository.findById(id);
        Optional<Musica> optionalMusica = this.musicaRepository.findById(idMusica);

        // Caso não ache o usuario, retornar um 404
        if (optionalUsuario.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        // Caso não ache a musica a ser associada, retornar um 404
        if (optionalMusica.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Usuario usuario = optionalUsuario.get();
        Musica musica = optionalMusica.get();

        usuario.getPlaylists().get(0).getMusicas().add(musica);
        playlistRepository.save(usuario.getPlaylists().get(0));

        return new ResponseEntity(usuario, HttpStatus.OK);
    }

    @PostMapping("{id}/desfavoritar/{idMusica}")
    public ResponseEntity desfavoritar(@PathVariable("id")UUID id, @PathVariable("idMusica")UUID idMusica) {

        // Faço as buscas do usuario e musica
        Optional<Usuario> optionalUsuario = this.repository.findById(id);
        Optional<Musica> optionalMusica = this.musicaRepository.findById(idMusica);

        // Caso não ache o usuario, retornar um 404
        if (optionalUsuario.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        // Caso não ache a musica a ser associada, retornar um 404
        if (optionalMusica.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Usuario usuario = optionalUsuario.get();
        Musica musica = optionalMusica.get();

        for (Musica item : usuario.getPlaylists().get(0).getMusicas()) {
            if (item.getId() == musica.getId()) {
                usuario.getPlaylists().get(0).getMusicas().remove(musica);
                break;
            }
        }
        playlistRepository.save(usuario.getPlaylists().get(0));

        return new ResponseEntity(usuario, HttpStatus.OK);
    }

    @PostMapping("{id}/criar-playlist")
    public ResponseEntity criarPlaylist(@PathVariable("id")UUID id, @Valid @RequestBody PlaylistRequest request) {
        Optional<Usuario> optionalUsuario = this.repository.findById(id);

        // Caso não ache o usuario, retornar um 404
        if (optionalUsuario.isEmpty() == true) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Usuario usuario = optionalUsuario.get();

        Playlist playlist = new Playlist();
        playlist.setUsuario(usuario);
        playlist.setNome(request.getNome());

        playlistRepository.save(playlist);

        return new ResponseEntity(usuario, HttpStatus.OK);
    }

    @PostMapping("{id}/playlist{idPlaylist}/adicionar/{idMusica}")
    public ResponseEntity adicionarMusicaPlaylist(@PathVariable("id")UUID id, @PathVariable("idPlaylist")UUID idPlaylist, @PathVariable("idMusica")UUID idMusica) {

        // Faço as buscas do usuario e musica
        Optional<Usuario> optionalUsuario = this.repository.findById(id);
        Optional<Musica> optionalMusica = this.musicaRepository.findById(idMusica);
        Optional<Playlist> optionalPlaylist = this.playlistRepository.findById(idPlaylist);

        // Caso não ache o usuario, retornar um 404
        if (optionalUsuario.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        // Caso não ache a musica a ser associada, retornar um 404
        if (optionalMusica.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Playlist playlist = optionalPlaylist.get();
        Musica musica = optionalMusica.get();

        // Adiciona na playlist
        playlist.getMusicas().add(musica);

        // Salva no banco de dados
        playlistRepository.save(playlist);

        return new ResponseEntity(optionalUsuario.get(), HttpStatus.OK);
    }

    @DeleteMapping("{id}/playlist{idPlaylist}/remover/{idMusica}")
    public ResponseEntity removerMusicaPlaylist(@PathVariable("id")UUID id, @PathVariable("idPlaylist")UUID idPlaylist, @PathVariable("idMusica")UUID idMusica) {

        // Faço as buscar do usuario e musica
        Optional<Usuario> optionalUsuario = this.repository.findById(id);
        Optional<Musica> optionalMusica = this.musicaRepository.findById(idMusica);
        Optional<Playlist> optionalPlaylist = this.playlistRepository.findById(idPlaylist);

        // Caso não ache o usuario, retornar um 404
        if (optionalUsuario.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        // Caso não ache a musica a ser associada, retornar um 404
        if (optionalMusica.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Playlist playlist = optionalPlaylist.get();
        Musica musica = optionalMusica.get();

        // Remove da playlist
        for (Musica item : playlist.getMusicas()) {
            if (item.getId() == musica.getId()) {
                playlist.getMusicas().remove(musica);
                break;
            }
        }

        // Salva no banco de dados
        playlistRepository.save(playlist);

        return new ResponseEntity(optionalUsuario.get(), HttpStatus.OK);
    }
}
