package com.felipe.helpdesk.domain.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.felipe.helpdesk.domain.Chamado;
import com.felipe.helpdesk.domain.Tecnico;
import com.felipe.helpdesk.domain.enums.Perfil;

public class TecnicoDTO implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	@NotNull(message = "O campo NOME é requerido!")
	protected String nome;
	
	@CPF
	@NotNull(message = "O campo CPF é requerido!")
	protected String cpf;
	
	@NotNull(message = "O campo EMAIL é requerido!")
	protected String email;
	
	@NotNull(message = "O campo SENHA é requerido!")
	protected String senha;	
	protected Set<Integer> perfis = new HashSet<>();
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	protected LocalDate dataCriacao = LocalDate.now();
	
	private List<ChamadoDTO> chamadosDTO = new ArrayList<>();

	public TecnicoDTO() {
		super();
	}

	public TecnicoDTO(Tecnico tecnico) {
		super();
		this.id = tecnico.getId();
		this.nome = tecnico.getNome();
		this.cpf = tecnico.getCpf();
		this.email = tecnico.getEmail();
		this.senha = tecnico.getSenha();
		this.perfis = tecnico.getPerfis().stream()
				.map(perfil -> perfil.getCodigo())
				.collect(Collectors.toSet());
		this.dataCriacao = tecnico.getDataCriacao();
	}
	
	public TecnicoDTO(Tecnico tecnico, List<Chamado> chamados) {
		this(tecnico);
		chamados.forEach(chamado -> chamadosDTO.add(new ChamadoDTO(chamado)));
	}	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream()
				.map(perfil -> Perfil.toEnum(perfil))
				.collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCodigo());
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public List<ChamadoDTO> getChamadosDTO() {
		return chamadosDTO;
	}
}
