package com.algaworks.algalog.api.controller;



import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;
import com.algaworks.algalog.domain.service.CadastroClienteService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private CadastroClienteService cadastroClienteService;

	@GetMapping
	public List<Cliente> listarClientes() {
		return cadastroClienteService.listar();
	}
	
	@GetMapping("/{clientId}")
	public ResponseEntity<Cliente> buscarCliente(@PathVariable Long clientId) {
		return cadastroClienteService.buscaCliente(clientId)
				//.map(cliente -> ResponseEntity.ok(cliente))
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
		/*
		 * Optional<Cliente> cliente = clienteRepository.findById(clientId); if
		 * (cliente.isPresent()){ return ResponseEntity.ok(cliente.get()); }else {
		 * return ResponseEntity.notFound().build(); }
		 */
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente incluirCliente(@RequestBody @Valid Cliente cliente) {
		return cadastroClienteService.salvar(cliente);
	}

	@PutMapping("/{idCliente}")
	public ResponseEntity<Cliente> alteraCliente(@PathVariable Long idCliente,
												 @RequestBody  @Valid Cliente cliente) {
		if(!cadastroClienteService.existeId(idCliente)) {
			return ResponseEntity.notFound().build();
		}else {
			cliente.setId(idCliente);
			cliente =  cadastroClienteService.salvar(cliente);
			return ResponseEntity.ok(cliente);		
		}
	}
	
	@DeleteMapping("/{idCliente}")
	public ResponseEntity<Void> excluirCliente(@PathVariable Long idCliente) {
		if(!cadastroClienteService.existeId(idCliente)) {
			return ResponseEntity.notFound().build();
		}else {
			 cadastroClienteService.exluir(idCliente);
			return ResponseEntity.noContent().build();		
		}
	}
}
