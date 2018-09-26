package com.SRMAsset.InterestManager.service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SRMAsset.InterestManager.helper.InterestTax;
import com.SRMAsset.InterestManager.model.Client;
import com.SRMAsset.InterestManager.repository.ClientRepository;

@Service
public class ClientService {

	private ClientRepository clientRepository;

	@Autowired
	public ClientService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	public Client insertClient(Client client) {
		client.setModified(Date.from(Instant.now()));
		this.checkInterestRate(client);
		return clientRepository.insert(client);
	}

	public Optional<Client> findById(String id) {
		return clientRepository.findById(id);
	}

	public Iterable<Client> findAll() {
		return clientRepository.findAll();
	}

	public Client updateClient(Client client) {
		client.setModified(Date.from(Instant.now()));
		this.checkInterestRate(client);
		return clientRepository.save(client);
	}

	public void deleteClient(String id) {
		clientRepository.deleteById(id);
	}

	public void checkInterestRate(Client client) {
		InterestTax tax = InterestTax.valueOf(client.getRisk().toUpperCase());
		client.setInterestRate(tax.getTax());
	}
}