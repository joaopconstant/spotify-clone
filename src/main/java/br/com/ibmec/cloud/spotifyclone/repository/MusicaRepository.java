package br.com.ibmec.cloud.spotifyclone.repository;

import br.com.ibmec.cloud.spotifyclone.models.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MusicaRepository extends JpaRepository<Musica, UUID> {

}
