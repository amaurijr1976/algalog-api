package com.algaworks.algalog.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algalog.domain.exception.NegocioException;
import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CadastroClienteService {

	private ClienteRepository clienteRepository;
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
				.stream()
				.anyMatch( clienteExistente -> !clienteExistente.equals(cliente));
		if (emailEmUso){
			throw new NegocioException("Já existe um cliente cadastrado com esse email");
		}
		return clienteRepository.save(cliente);
	}
	
	@Transactional
	public void exluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}
	
	
	public Boolean existeId(Long clienteId) {
		return clienteRepository.existsById(clienteId);
	}
	
	public List<Cliente> listar(){
		return clienteRepository.findAll();
	}

	public Optional<Cliente> buscaCliente(Long clientId) {
		return clienteRepository.findById(clientId);
	}
	
}
