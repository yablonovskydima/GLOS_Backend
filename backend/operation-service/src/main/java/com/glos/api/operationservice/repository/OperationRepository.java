package com.glos.api.operationservice.repository;

import com.glos.api.operationservice.Operation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OperationRepository extends MongoRepository<Operation, UUID> {

    @Query("{ 'data.username': '?0', 'action': { $regex: '^?1$', $options: 'i' } }")
    Optional<Operation> findByUsernameAndAction(String username, String action);

    @Query("{'code': ?0}")
    Optional<Operation> findByCode(String code);

}
