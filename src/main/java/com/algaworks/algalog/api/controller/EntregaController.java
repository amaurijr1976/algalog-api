package com.algaworks.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.api.mappers.EntregaMapper;
import com.algaworks.algalog.api.model.EntregaModel;
import com.algaworks.algalog.api.model.input.EntregaInput;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.service.FinalizacaoEntregaService;
import com.algaworks.algalog.domain.service.SolicitacaoEntregaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {

	private SolicitacaoEntregaService solicitacaoEntregaService;
	private EntregaMapper entregaMapper;
	private FinalizacaoEntregaService finalizacaoEntregaService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntregaModel solicitar(@RequestBody @Valid EntregaInput	entregaInput) {
		Entrega entregaNova = entregaMapper.toEntity(entregaInput); 
		Entrega entregaSolicitada = solicitacaoEntregaService.solicitar(entregaNova);
		return entregaMapper.toModel(entregaSolicitada);
	}
	
	@GetMapping
	public List<EntregaModel> listarTodasEntregas(){
		return entregaMapper.toModelCollection(solicitacaoEntregaService.listarEntrega());
	}
	
	@GetMapping("/{idEntrega}")
	public ResponseEntity<EntregaModel> listaEntrega(@PathVariable Long idEntrega){
		Entrega entrega = solicitacaoEntregaService.buscarEntrega(idEntrega);
		return ResponseEntity.ok(entregaMapper.toModel(entrega));			
	}
	
	@PutMapping("/{idEntrega}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizaEntrega(@PathVariable Long idEntrega){
		finalizacaoEntregaService.finalizar(idEntrega);
	}
	
}
