package com.felipe.helpdesk.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.felipe.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	@Query("SELECT DISTINCT c FROM Cliente c LEFT JOIN FETCH c.chamados WHERE c.id = :id ")
	Optional<Cliente> fetchClienteWithChamados(@Param("id") Integer id);
}
