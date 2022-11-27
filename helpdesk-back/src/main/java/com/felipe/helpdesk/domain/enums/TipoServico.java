package com.felipe.helpdesk.domain.enums;

public enum TipoServico {

	REPARO_NOTEBOOK(0, "REPARO DE NOTEBOOK"),
	REPARO_MONITOR(1, "REPARO DE MONITOR"),
	REPARO_CPU(2, "REPARO DE CPU"),
	FORMATACAO_NOTEBOOK(3, "FORMATACAO DE NOTEBOOK"),
	FORMATACAO_CPU(4, "FORMATACAO DE CPU"),
	PROBLEMA_CONEXAO(5, "PROBLEMA DE CONEXAO"),
	CONFIGURACAO_IMPRESSORA(6, "CONFIGURACAO DE IMPRESSORA");

	private Integer codigo;
	private String descricao;

	TipoServico(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoServico toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}
		
		for (TipoServico prioridade : TipoServico.values()) {
			if (codigo.equals(prioridade.getCodigo())) {
				return prioridade;
			}
		}
		
		throw new IllegalArgumentException("Status inválido");
	}	
	
}
