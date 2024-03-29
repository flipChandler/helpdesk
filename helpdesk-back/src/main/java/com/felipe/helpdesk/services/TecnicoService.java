package com.felipe.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felipe.helpdesk.domain.Pessoa;
import com.felipe.helpdesk.domain.Tecnico;
import com.felipe.helpdesk.domain.dto.TecnicoDTO;
import com.felipe.helpdesk.domain.enums.Perfil;
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
	private BCryptPasswordEncoder encoder;	// criptografa a senha no banco de dados
	
	public TecnicoDTO findById(Integer id){
		return new TecnicoDTO(tecnicoRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id = " + id)));
	}
	
	public List<TecnicoDTO> findAll() {
		return tecnicoRepository.findAll()
				.stream()
				.map(TecnicoDTO::new)
				.collect(Collectors.toList()) ;
	}

	@Transactional
	public TecnicoDTO create(TecnicoDTO dto) {
		dto.setId(null);
		dto.setSenha(encoder.encode(dto.getSenha()));

		validarPorCpfEEmail(dto);
		dto.setDataCriacao(LocalDate.now());
		dto.addPerfil(Perfil.CLIENTE);
		Tecnico tecnico = tecnicoRepository.save(new Tecnico(dto));			
		
		return new TecnicoDTO(tecnico);
	}
	
	@Transactional
	public TecnicoDTO update(Integer id, TecnicoDTO dto) {
		dto.setId(id);
		TecnicoDTO oldDTO = findById(id);
		
		// se a senha foi alterada, encoda novamente com a nova senha
		if (!dto.getSenha().equals(oldDTO.getSenha())) { 
			dto.setSenha(encoder.encode(dto.getSenha()));			
		}
		BeanUtils.copyProperties(dto, oldDTO, "id"); // copia dto em oldDTO

		dto.getPerfis()
				.stream()
				.forEach(perfil -> oldDTO.addPerfil(perfil));
		
		validarPorCpfEEmail(oldDTO); // validar o objeto passado por parametro | se não validar, lança uma exceção
		
		Tecnico tecnicoAtualizado = tecnicoRepository.save(new Tecnico(oldDTO));
		return new TecnicoDTO(tecnicoAtualizado); 
	}

	public void delete(Integer id) {
		Optional<Tecnico> optional = tecnicoRepository.fetchTecnicoWithChamados(id);
		
		if (optional.isPresent() && optional.get().getChamados().size() > 0) {
			throw new DataIntegrityViolationException(MessageUtils.TECNICO_POSSUI_ORDENS_SERVICO);
		}
		
		if (optional.isEmpty()) {
			throw new DataIntegrityViolationException(MessageUtils.TECNICO_NAO_EXISTE);
		}
						
		tecnicoRepository.deleteById(id);
	}

	private void validarPorCpfEEmail(TecnicoDTO dto) {
		Optional<Pessoa> optional = pessoaRepository.findByCpf(dto.getCpf());
		if (optional.isPresent() && optional.get().getId() != dto.getId()) {
			throw new DataIntegrityViolationException(MessageUtils.CPF_ALREADY_EXISTS);
		}
		
		optional = pessoaRepository.findByEmail(dto.getEmail());
		if (optional.isPresent() && optional.get().getId() != dto.getId()) {
			throw new DataIntegrityViolationException(MessageUtils.EMAIL_ALREADY_EXISTS);
		}
	}
}

