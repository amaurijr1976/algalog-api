package com.algaworks.algalog.domain.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.algaworks.algalog.domain.model.Entrega;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FinalizacaoEntregaService {

	private SolicitacaoEntregaService solicitacaoEntregaService;
	
	@Transactional
	public void finalizar(Long entregaId) {
		Entrega entrega = solicitacaoEntregaService.buscarEntrega(entregaId);
		entrega.finalizar();
		solicitacaoEntregaService.salvar(entrega);
	}
	
}
