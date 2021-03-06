package com.felipe.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.felipe.helpdesk.domain.dto.TecnicoDTO;

@Entity
public class Tecnico extends Pessoa {
	
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@OneToMany(mappedBy = "tecnico", fetch = FetchType.LAZY) // um tecnico para n chamados | "tecnico" é o objeto que está em Chamado
	private List<Chamado> chamados = new ArrayList<>();
	
	
	public Tecnico() {
		super();
	}

	
	public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
	}
	
	public Tecnico(TecnicoDTO dto) {
		super();
		this.id = dto.getId();
		this.nome = dto.getNome();
		this.cpf = dto.getCpf();
		this.email = dto.getEmail();
		this.senha = dto.getSenha();
		this.perfis = dto.getPerfis().stream()
				.map(perfil -> perfil.getCodigo())
				.collect(Collectors.toSet());
		this.dataCriacao = dto.getDataCriacao();
		this.chamados = dto.getChamadosDTO().stream()
				.map(chamado -> new Chamado(chamado))
				.collect(Collectors.toList());
	}

	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}
	
	
}
