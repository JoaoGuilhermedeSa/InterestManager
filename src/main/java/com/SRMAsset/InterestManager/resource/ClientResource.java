package com.SRMAsset.InterestManager.resource;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SRMAsset.InterestManager.error.ErrorResponse;
import com.SRMAsset.InterestManager.model.Client;
import com.SRMAsset.InterestManager.service.ClientService;

@CrossOrigin(origins = "http://127.0.0.1:8080")
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
		log.debug("Buscando lista de clientes {}");
		return ResponseEntity.ok(clientService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) {
		log.debug("Buscando cliente de id " + id);
			Optional<Client> client = clientService.findById(id);
			if (client.isPresent()) {
				return ResponseEntity.ok(client.get());
			} else {
				return ResponseEntity.status(404).body(
						new ErrorResponse(HttpStatus.NOT_FOUND, "Client with this id is not present on the database "));
			}
	}

	@PostMapping
	public ResponseEntity<?> findById(@RequestBody @Valid Client client) {
		log.debug("Inserindo cliente novo - " + client.getName());
		log.warn(client.getName() + "--" + client.getRisk() + "--" + client.getCreditLimit());
			return ResponseEntity.ok(clientService.insertClient(client));
	}

	@PutMapping
	public ResponseEntity<?> updateClient(@RequestBody @Valid Client client) {
		log.debug("Atualizando cliente - " + client.getId());
			return ResponseEntity.ok(clientService.updateClient(client));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> updateClient(@PathVariable String id) {
		log.debug("Excluindo cliente - " + id);
			clientService.deleteClient(id);
			return ResponseEntity.accepted().build();
	}

}
