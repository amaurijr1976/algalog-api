package com.algaworks.algalog.api.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClienteResumoModel {

	private Long id;
	private String nome;
}
