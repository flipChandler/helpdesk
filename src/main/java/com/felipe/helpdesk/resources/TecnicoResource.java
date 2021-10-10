package com.felipe.helpdesk.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipe.helpdesk.domain.dto.TecnicoDTO;
import com.felipe.helpdesk.services.TecnicoService;

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
		TecnicoDTO tecnicoDTO = tecnicoService.findById(id);
		return ResponseEntity.ok(tecnicoDTO);
	}
	
	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> finAll() {
		List<TecnicoDTO> list = tecnicoService.findAll();
		return ResponseEntity.ok(list);
	}
	
	
}
