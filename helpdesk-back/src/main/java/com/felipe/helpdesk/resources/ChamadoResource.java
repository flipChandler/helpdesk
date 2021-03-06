package com.felipe.helpdesk.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.felipe.helpdesk.domain.dto.ChamadoDTO;
import com.felipe.helpdesk.services.ChamadoService;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoResource {
	
	@Autowired
	private ChamadoService chamadoService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ChamadoDTO> findById(@PathVariable("id") Integer id) {
		ChamadoDTO dto = chamadoService.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping
	public ResponseEntity<List<ChamadoDTO>> findAll() {
		List<ChamadoDTO> dto = chamadoService.findAll();
		return ResponseEntity.ok().body(dto);
	}	
	
	@PostMapping
	public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO dto) {
		dto = chamadoService.create(dto);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(dto.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ChamadoDTO> update(@PathVariable Integer id, @Valid @RequestBody ChamadoDTO dto) {
		ChamadoDTO chamadoDTO = chamadoService.update(id, dto);
		return ResponseEntity.ok().body(chamadoDTO);
	}
}
