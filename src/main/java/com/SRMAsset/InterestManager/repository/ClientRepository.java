package com.SRMAsset.InterestManager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.SRMAsset.InterestManager.model.Client;

public interface ClientRepository extends MongoRepository<Client, String>{

}
