package com.felipe.helpdesk.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipe.helpdesk.TecnicoMapper;
import com.felipe.helpdesk.domain.Tecnico;
import com.felipe.helpdesk.domain.dto.TecnicoDTO;
import com.felipe.helpdesk.repositories.TecnicoRepository;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private TecnicoMapper tecnicoMapper;
	
	public TecnicoDTO findById(Integer id) {
		Optional<Tecnico> optional =  tecnicoRepository.findById(id);
		TecnicoDTO dto = tecnicoMapper.toDTO(optional.get());
		return dto;
	}
}
