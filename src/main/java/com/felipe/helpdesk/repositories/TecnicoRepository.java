package com.felipe.helpdesk.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.felipe.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {
	
	@Query("SELECT DISTINCT t FROM Tecnico t LEFT JOIN FETCH t.chamados WHERE t.id = :id ")
	Optional<Tecnico> fetchTecnicoWithChamados(@Param("id") Integer id);
	
}
