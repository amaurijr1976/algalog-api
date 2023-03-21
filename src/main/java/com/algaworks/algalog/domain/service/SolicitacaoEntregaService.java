package com.algaworks.algalog.domain.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.algaworks.algalog.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algalog.domain.exception.NegocioException;
import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.model.StatusEntrega;
import com.algaworks.algalog.domain.repository.EntregaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SolicitacaoEntregaService {

	private CadastroClienteService cadastroClienteService;
	private EntregaRepository entregaRepository;
	
	public Entrega solicitar(Entrega entrega) {
		Cliente cliente = cadastroClienteService.buscaCliente(entrega.getCliente().getId())
						  .orElseThrow(() -> new  NegocioException("Cliente não encontrado"));
		entrega.setCliente(cliente);
		entrega.setStatus(StatusEntrega.PENDENTE);
		entrega.setDataPedido(OffsetDateTime.now());
		return entregaRepository.save(entrega);
	}
	
	public List<Entrega> listarEntrega() {
		return entregaRepository.findAll();
	}

	public Entrega buscarEntrega(Long idEntrega) {
		Entrega entrega = entregaRepository.findById(idEntrega)
				                .orElseThrow(() -> new EntidadeNaoEncontradaException("Entrega não encontrada"));
		return entrega;
	}

	public void salvar(Entrega entrega) {
	    entregaRepository.save(entrega);		
	}
}
