package com.felipe.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.felipe.helpdesk.domain.dto.ClienteDTO;
import com.felipe.helpdesk.domain.enums.Perfil;

@Entity
public class Cliente extends Pessoa {	
	
	private static final long serialVersionUID = 1L;
	
	
	@JsonIgnore // evita recursão | Cliente tem chamados e Chamado tem cliente
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)  // um cliente para n chamados
	private List<Chamado> chamados = new ArrayList<>();

	public Cliente() {
		super();
		addPerfil(Perfil.CLIENTE);
	}

	public Cliente(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
		addPerfil(Perfil.CLIENTE);
	}
	
	public Cliente(ClienteDTO dto) {
		super();
		this.id = dto.getId();
		this.nome = dto.getNome();
		this.cpf = dto.getCpf();
		this.email = dto.getEmail();
		this.senha = dto.getSenha();
		this.perfis = dto.getPerfis().stream()
				.map(perfil -> perfil.getCodigo())
				.collect(Collectors.toSet());
	}

	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}	
	
}
