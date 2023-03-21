package com.algaworks.algalog.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter	
public class DestinatarioInput {

	@NotBlank
	private String nome;
	
	@NotBlank
	private String endereco;
	
	@NotBlank
	private String numero;
	
	@NotBlank
	private String complemento;
	
	@NotBlank
	private String bairro;
	
	@NotBlank
	private String cidade;
	
	@NotBlank
	private String estado;

	@NotNull
	private Integer CEP;
	
	
}
