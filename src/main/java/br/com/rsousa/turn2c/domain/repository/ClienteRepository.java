package br.com.rsousa.turn2c.domain.repository;

import br.com.rsousa.turn2c.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
