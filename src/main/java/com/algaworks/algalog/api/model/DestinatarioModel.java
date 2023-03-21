package com.algaworks.algalog.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DestinatarioModel {

	private String nome;
	private String endereco;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private Integer CEP;	
}
