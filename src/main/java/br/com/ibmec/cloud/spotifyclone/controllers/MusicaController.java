package br.com.ibmec.cloud.spotifyclone.controllers;

import br.com.ibmec.cloud.spotifyclone.models.Musica;
import br.com.ibmec.cloud.spotifyclone.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("musicas")
public class MusicaController {

    @Autowired
    private MusicaRepository musicaRepository;

    @GetMapping
    public List<Musica> listaMusicas() {
        return musicaRepository.findAll();
    }
}
