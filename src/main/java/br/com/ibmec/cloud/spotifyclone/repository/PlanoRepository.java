package br.com.ibmec.cloud.spotifyclone.repository;

import br.com.ibmec.cloud.spotifyclone.models.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, UUID> {

}
