package com.felipe.helpdesk.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipe.helpdesk.TecnicoMapper;
import com.felipe.helpdesk.domain.Tecnico;
import com.felipe.helpdesk.domain.dto.TecnicoDTO;
import com.felipe.helpdesk.repositories.TecnicoRepository;
import com.felipe.helpdesk.services.exceptions.BusinessException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private TecnicoMapper tecnicoMapper;
	
	public TecnicoDTO findById(Integer id){
		Optional<Tecnico> optional =  tecnicoRepository.findById(id);
		if (optional.isEmpty()) {
			throw new BusinessException("Objeto não encontrado! Id = " + id);
		}
		
		TecnicoDTO dto = tecnicoMapper.toDTO(optional.get());
		return dto;
	}

	public List<TecnicoDTO> findAll() {
		List<Tecnico> list = tecnicoRepository.findAll();
		return list.stream().map(t -> new TecnicoDTO(t)).collect(Collectors.toList()) ;
	}
}
