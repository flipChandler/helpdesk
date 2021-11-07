package com.felipe.helpdesk.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felipe.helpdesk.domain.Cliente;
import com.felipe.helpdesk.domain.Pessoa;
import com.felipe.helpdesk.domain.dto.ClienteDTO;
import com.felipe.helpdesk.repositories.ClienteRepository;
import com.felipe.helpdesk.repositories.PessoaRepository;
import com.felipe.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.felipe.helpdesk.services.exceptions.ObjectNotFoundException;
import com.felipe.helpdesk.util.MessageUtils;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;	
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	public ClienteDTO findById(Integer id){
		Optional<Cliente> optional =  clienteRepository.findById(id);
		if (optional.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id = " + id);
		}		
		
		return new ClienteDTO(optional.get());
	}

	public List<ClienteDTO> findAll() {
		return clienteRepository.findAll()
				.stream().map(entity -> new ClienteDTO(entity))
				.collect(Collectors.toList()) ;
	}

	@Transactional
	public ClienteDTO create(ClienteDTO dto) {
		dto.setId(null);
		validarPorCpfEEmail(dto);
		Cliente cliente = clienteRepository.save(new Cliente(dto));			
		
		return new ClienteDTO(cliente);
	}
	
	@Transactional
	public ClienteDTO update(Integer id, @Valid ClienteDTO dto) {
		dto.setId(id);
		ClienteDTO oldDTO = findById(id);
		BeanUtils.copyProperties(dto, oldDTO, "id"); // copia dto em oldDTO
		
		validarPorCpfEEmail(oldDTO); // validar o objeto passado por parametro | se não validar, lança uma exceção
		
		Cliente tecnicoAtualizado = clienteRepository.save(new Cliente(oldDTO));
		return new ClienteDTO(tecnicoAtualizado); 
	}

	@Transactional
	public void delete(Integer id) {
		Optional<Cliente> optional = clienteRepository.fetchClienteWithChamados(id);
		
		if (optional.isPresent() && optional.get().getChamados().size() > 0) {
			throw new DataIntegrityViolationException(MessageUtils.TECNICO_POSSUI_ORDENS_SERVICO);
		}
		
		if (optional.isEmpty()) {
			throw new DataIntegrityViolationException(MessageUtils.TECNICO_NAO_EXISTE);
		}
						
		clienteRepository.deleteById(id);
	}

	private void validarPorCpfEEmail(ClienteDTO dto) {
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

