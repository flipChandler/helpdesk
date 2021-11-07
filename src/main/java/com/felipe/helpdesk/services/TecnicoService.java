package com.felipe.helpdesk.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	public TecnicoDTO findById(Integer id){
		Optional<Tecnico> optional =  tecnicoRepository.findById(id);
		if (optional.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id = " + id);
		}		
		
		return new TecnicoDTO(optional.get());
	}

	public List<TecnicoDTO> findAll() {
		return tecnicoRepository.findAll()
				.stream().map(entity -> new TecnicoDTO(entity))
				.collect(Collectors.toList()) ;
	}

	@Transactional
	public TecnicoDTO create(TecnicoDTO dto) {
		dto.setId(null);
		validarPorCpfEEmail(dto);
		Tecnico tecnico = tecnicoRepository.save(new Tecnico(dto));			
		
		return new TecnicoDTO(tecnico);
	}
	
	@Transactional
	public TecnicoDTO update(Integer id, TecnicoDTO dto) {
		dto.setId(id);
		Optional<Tecnico> optional = tecnicoRepository.findById(id);
		
		Tecnico tecnico = optional.get();
		BeanUtils.copyProperties(dto, tecnico, "id");
		
		tecnico = tecnicoRepository.save(tecnico);
		return new TecnicoDTO(tecnico); 
	}

	public void delete(Integer id) {
		TecnicoDTO dto = findById(id);
		if (dto.getChamados().size() > 0) {
			throw new DataIntegrityViolationException(MessageUtils.TECNICO_POSSUI_ORDENS_SERVICO);
		}
		
		try {
			tecnicoRepository.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
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
