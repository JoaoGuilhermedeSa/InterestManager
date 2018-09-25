package com.SRMAsset.InterestManager.resource;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SRMAsset.InterestManager.error.ErrorResponse;
import com.SRMAsset.InterestManager.model.Client;
import com.SRMAsset.InterestManager.service.ClientService;

@RestController
@RequestMapping("api/client")
public class ClientResource {

	private ClientService clientService;

	@Autowired
	public ClientResource(ClientService clientService) {
		this.clientService = clientService;
	}

	private static final Logger log = LoggerFactory.getLogger(ClientResource.class);

	@GetMapping
	public ResponseEntity<?> findAll() {
		try {
			log.debug("Buscando lista de clientes {}");
			return ResponseEntity.ok(clientService.findAll());
		} catch (Exception ex) {
			log.error("Erro ao buscar clientes ", ex);
			return ResponseEntity.status(500)
					.body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) {
		log.debug("Buscando cliente de id " + id);
		try {
			Optional<Client> client = clientService.findById(id);
			if (client.isPresent()) {
				return ResponseEntity.ok(client.get());
			} else {
				return ResponseEntity.status(404).body(
						new ErrorResponse(HttpStatus.NOT_FOUND, "Client with this id is not present on the database "));
			}
		} catch (Exception ex) {
			log.error("Erro ao buscar cliente " + id, ex);
			return ResponseEntity.status(500)
					.body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
		}
	}

	@PostMapping
	public ResponseEntity<?> findById(@Valid Client client) {
		log.debug("Inserindo cliente novo - " + client.getName());
		try {
			return ResponseEntity.ok(clientService.insertClient(client));
		} catch (Exception ex) {
			log.error("Erro ao inserir cliente ", ex);
			return ResponseEntity.status(500)
					.body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
		}
	}

	@PutMapping
	public ResponseEntity<?> updateClient(@Valid Client client) {
		log.debug("Atualizando cliente - " + client.getId());
		try {
			return ResponseEntity.ok(clientService.updateClient(client));
		} catch (Exception ex) {
			log.error("Erro ao atualizar cliente " + client.getId(), ex);
			return ResponseEntity.status(500)
					.body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> updateClient(@PathVariable String id) {
		log.debug("Excluindo cliente - " + id);
		try {
			clientService.deleteClient(id);
			return ResponseEntity.accepted().build();
		} catch (Exception ex) {
			log.error("Erro ao atualizar cliente " + id, ex);
			return ResponseEntity.status(500)
					.body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
		}
	}

}
