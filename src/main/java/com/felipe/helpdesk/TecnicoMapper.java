package com.felipe.helpdesk;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.felipe.helpdesk.domain.Tecnico;
import com.felipe.helpdesk.domain.dto.TecnicoDTO;
import com.felipe.helpdesk.domain.enums.Perfil;

@Component
public class TecnicoMapper {
	
	public Tecnico toEntity(TecnicoDTO dto) {
		Tecnico entity = new Tecnico();
		entity.setId(dto.getId());
		entity.setNome(dto.getNome());
		entity.setCpf(dto.getCpf());
		entity.setEmail(dto.getEmail());
		entity.setSenha(dto.getSenha());
		entity.setDataCriacao(dto.getDataCriacao());
		entity.setPerfis(dto.getPerfis().stream()
				.map(p -> p.getCodigo()).collect(Collectors.toSet()));
		return entity;
	}
	
	public TecnicoDTO toDTO(Tecnico entity) {
		TecnicoDTO dto = new TecnicoDTO();
		dto.setId(entity.getId());
		dto.setNome(entity.getNome());
		dto.setCpf(entity.getCpf());
		dto.setEmail(entity.getEmail());
		dto.setSenha(entity.getSenha());
		dto.setDataCriacao(entity.getDataCriacao());	
		dto.setPerfis(entity.getPerfis().stream()
				.map(p -> Perfil.toEnum(p.getCodigo())).collect(Collectors.toSet()));
		return dto;
	}
}
