package com.felipe.helpdesk.domain.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.felipe.helpdesk.domain.Chamado;


public class ChamadoDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataAbertura = LocalDate.now();
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFechamento;

	@NotNull(message = "O campo PRIORIDADE é requerido")
	private Integer prioridade;

	@NotNull(message = "O campo STATUS é requerido")
	private Integer status;
	
	@NotNull(message = "O campo TÍTULO é requerido")
	private String titulo;
	
	@NotNull(message = "O campo OBSERVAÇÕES é requerido")
	private String observacoes;
	
	private Integer tecnico;	
	private Integer cliente;
	private String nomeTecnico;
	private String nomeCliente;
		
	public ChamadoDTO() {
		super();
	}

	public ChamadoDTO(Chamado entity) {
		super();
		this.id = entity.getId();
		this.dataAbertura = entity.getDataAbertura();
		this.dataFechamento = entity.getDataFechamento();
		this.prioridade = entity.getPrioridade().getCodigo();
		this.status = entity.getStatus().getCodigo();
		this.titulo = entity.getTitulo();
		this.observacoes = entity.getObservacoes();
		this.tecnico = entity.getTecnico().getId();
		this.cliente = entity.getCliente().getId();
		this.nomeTecnico = entity.getTecnico().getNome();
		this.nomeCliente = entity.getCliente().getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDate dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public LocalDate getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(LocalDate dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public Integer getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Integer prioridade) {
		this.prioridade = prioridade;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Integer getTecnico() {
		return tecnico;
	}

	public void setTecnico(Integer tecnico) {
		this.tecnico = tecnico;
	}

	public Integer getCliente() {
		return cliente;
	}

	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}

	public String getNomeTecnico() {
		return nomeTecnico;
	}

	public void setNomeTecnico(String nomeTecnico) {
		this.nomeTecnico = nomeTecnico;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}	
}
