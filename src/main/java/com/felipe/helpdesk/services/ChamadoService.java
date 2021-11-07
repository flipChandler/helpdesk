package com.felipe.helpdesk.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipe.helpdesk.domain.Chamado;
import com.felipe.helpdesk.domain.dto.ChamadoDTO;
import com.felipe.helpdesk.repositories.ChamadoRepository;
import com.felipe.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	public ChamadoDTO findById(Integer id) {
		Optional<Chamado> optional = chamadoRepository.findById(id);
		if (optional.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id = " + id);
		}
		return new ChamadoDTO(optional.get());
	}
}
