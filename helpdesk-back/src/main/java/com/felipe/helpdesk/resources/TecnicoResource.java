package com.felipe.helpdesk.resources;


import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
//	@PreAuthorize("hasAnyRole('ADMIN')") // quem tiver esse perfil, pode acessar esse endpoint
	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO dto) {
		dto = tecnicoService.create(dto);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(dto.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	//@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value ="/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO dto) {
		TecnicoDTO tecnicoDTO = tecnicoService.update(id, dto);
		return ResponseEntity.ok().body(tecnicoDTO);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> delete(@PathVariable Integer id) {
		tecnicoService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
