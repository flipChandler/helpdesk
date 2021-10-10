package com.felipe.helpdesk.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felipe.helpdesk.TecnicoMapper;
import com.felipe.helpdesk.domain.Pessoa;
import com.felipe.helpdesk.domain.Tecnico;
import com.felipe.helpdesk.domain.dto.TecnicoDTO;
import com.felipe.helpdesk.repositories.PessoaRepository;
import com.felipe.helpdesk.repositories.TecnicoRepository;
import com.felipe.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.felipe.helpdesk.services.exceptions.ObjectNotFoundException;
import com.felipe.helpdesk.util.MessageUtils;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private TecnicoMapper tecnicoMapper;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public TecnicoDTO findById(Integer id){
		Optional<Tecnico> optional =  tecnicoRepository.findById(id);
		if (optional.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id = " + id);
		}
		
		TecnicoDTO dto = tecnicoMapper.toDTO(optional.get());
		return dto;
	}

	public List<TecnicoDTO> findAll() {
		List<Tecnico> list = tecnicoRepository.findAll();
		return list.stream().map(t -> new TecnicoDTO(t)).collect(Collectors.toList()) ;
	}

	@Transactional
	public TecnicoDTO create(TecnicoDTO dto) {
		dto.setId(null);
		validarPorCpfEEmail(dto);
		Tecnico tecnico = tecnicoMapper.toEntity(dto);
		tecnico = tecnicoRepository.save(tecnico);
		dto = tecnicoMapper.toDTO(tecnico);		
		
		return dto;
	}

	private void validarPorCpfEEmail(TecnicoDTO dto) {
		Optional<Pessoa> optional = pessoaRepository.findByCpf(dto.getCpf());
		if (optional.isPresent()) {
			throw new DataIntegrityViolationException(MessageUtils.CPF_ALREADY_EXISTS);
		}
		
		optional = pessoaRepository.findByEmail(dto.getEmail());
		if (optional.isPresent()) {
			throw new DataIntegrityViolationException(MessageUtils.EMAIL_ALREADY_EXISTS);
		}
	}
}
