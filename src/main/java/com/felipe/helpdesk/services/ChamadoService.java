package com.felipe.helpdesk.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipe.helpdesk.domain.Chamado;
import com.felipe.helpdesk.domain.Cliente;
import com.felipe.helpdesk.domain.Tecnico;
import com.felipe.helpdesk.domain.dto.ChamadoDTO;
import com.felipe.helpdesk.domain.dto.ClienteDTO;
import com.felipe.helpdesk.domain.dto.TecnicoDTO;
import com.felipe.helpdesk.domain.enums.Prioridade;
import com.felipe.helpdesk.domain.enums.Status;
import com.felipe.helpdesk.repositories.ChamadoRepository;
import com.felipe.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private TecnicoService tecnicoService;
	
	public ChamadoDTO findById(Integer id) {
		Optional<Chamado> optional = chamadoRepository.findById(id);
		if (optional.isEmpty()) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id = " + id);
		}
		return new ChamadoDTO(optional.get());
	}

	public List<ChamadoDTO> findAll() {
		List<Chamado> chamados = chamadoRepository.findAll();
		return chamados.stream()
				.map(chamado -> new ChamadoDTO(chamado))
				.collect(Collectors.toList());
	}

	public ChamadoDTO create(@Valid ChamadoDTO dto) {
		Chamado chamado = chamadoRepository.save(newChamado(dto));
		return new ChamadoDTO(chamado);
	}
	
	private Chamado newChamado(ChamadoDTO dto) {
		TecnicoDTO tecnicoDTO = tecnicoService.findById(dto.getTecnico());
		ClienteDTO clienteDTO = clienteService.findById(dto.getCliente());
		
		Chamado chamado = new Chamado();
		if (dto.getId() != null) {
			chamado.setId(dto.getId());
		}
		
			chamado.setTecnico(new Tecnico(tecnicoDTO));
			chamado.setCliente(new Cliente(clienteDTO));
			chamado.setPrioridade(Prioridade.toEnum(dto.getPrioridade()));
			chamado.setStatus(Status.toEnum(dto.getStatus()));
			chamado.setTitulo(dto.getTitulo());
			chamado.setObservacoes(dto.getObservacoes());
		return chamado;
	}
}
