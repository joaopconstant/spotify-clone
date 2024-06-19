package br.com.ibmec.cloud.spotifyclone.controllers;

import br.com.ibmec.cloud.spotifyclone.controllers.request.BandaRequest;
import br.com.ibmec.cloud.spotifyclone.controllers.request.MusicaRequest;
import br.com.ibmec.cloud.spotifyclone.models.Banda;
import br.com.ibmec.cloud.spotifyclone.models.Musica;
import br.com.ibmec.cloud.spotifyclone.repository.BandaRepository;
import br.com.ibmec.cloud.spotifyclone.repository.MusicaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/banda")
public class BandaController {
    @Autowired
    private BandaRepository repository;

    @Autowired
    private MusicaRepository musicaRepository;

    @PostMapping
    public ResponseEntity<Banda> criar(@Valid @RequestBody BandaRequest request) {
        Banda banda = new Banda();
        banda.setNome(request.getNome());
        banda.setImagem(request.getImagem());
        banda.setDescricao(request.getDescricao());

        // Salva a banda no banco de dados
        this.repository.save(banda);

        // Verificar se o usuario enviou musicas
        for (MusicaRequest item : request.getMusicas()) {
            Musica musica = new Musica();
            musica.setId(UUID.randomUUID());
            musica.setNome(item.getNome());
            musica.setImagem(item.getImagem());
            musica.setDuracao(item.getDuracao());
            musica.setBanda(banda);

            // Associa a banda à musica
            banda.getMusicas().add(musica);

            // Salva no banco de dados
            this.musicaRepository.save(musica);
        }

        return new ResponseEntity<>(banda, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Banda> obter(@PathVariable("id") UUID id) {
        return this.repository.findById(id).map(item -> {
            return new ResponseEntity<>(item, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("{id}/musica")
    public ResponseEntity<Banda> associarMusica(@PathVariable("id") UUID id, @Valid @RequestBody MusicaRequest request) {
        Optional<Banda> optionalBanda = this.repository.findById(id);

        if (optionalBanda.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Hibernate Optional -> Se ele achou, obtenho a instancia
        Banda banda = optionalBanda.get();

        // Cria uma nova instancia de musica para salvar
        Musica musica = new Musica();
        musica.setNome(request.getNome());
        musica.setImagem(request.getImagem());
        musica.setDuracao(request.getDuracao());
        musica.setBanda(banda);
        banda.getMusicas().add(musica);

        // Salva e associa a banda
        this.musicaRepository.save(musica);

        // Responde ao usuário
        return new ResponseEntity<>(banda, HttpStatus.CREATED);
    }

    @GetMapping("{id}/musica")
    public ResponseEntity<List<Musica>> obterMusicas(@PathVariable("id") UUID id) {
        return this.repository.findById(id).map(item -> {
            return new ResponseEntity<>(item.getMusicas(), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/musica/{musicaId}")
    public ResponseEntity<Banda> obterBandaPorMusica(@PathVariable("musicaId") UUID musicaId) {
        return this.musicaRepository.findById(musicaId).map(musica -> {
            return new ResponseEntity<>(musica.getBanda(), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
