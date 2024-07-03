package com.glos.accessservice.facade;

import com.glos.accessservice.clients.AccessTypeApiClient;
import com.glos.accessservice.clients.RepositoryApiClient;
import com.glos.accessservice.entities.AccessType;
import com.glos.accessservice.entities.Repository;
import com.glos.accessservice.responseDTO.RepositoryDTO;
import com.glos.accessservice.responseMappers.RepositoryDTOMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RepositoryApiFacade
{
    private final RepositoryApiClient repositoryApiClient;
    private final AccessTypeApiClient accessTypeApiClient;
    private final RepositoryDTOMapper repositoryDTOMapper;

    public RepositoryApiFacade(RepositoryApiClient repositoryApiClient, AccessTypeApiClient accessTypeApiClient, RepositoryDTOMapper repositoryDTOMapper) {
        this.repositoryApiClient = repositoryApiClient;
        this.accessTypeApiClient = accessTypeApiClient;
        this.repositoryDTOMapper = repositoryDTOMapper;
    }

//    public ResponseEntity<?> repositoryAppendAccessType(String rootFullName, String name)
//    {
//        RepositoryDTO repositoryDTO = repositoryApiClient.getRepositoryByRootFullName(rootFullName).getBody();
//        AccessType accessType = accessTypeApiClient.getByName(name).getBody();
//        repositoryDTO.getAccessTypes().add(accessType);
//
//        Repository repository = repositoryDTOMapper.toEntity(repositoryDTO);
//
//        repositoryApiClient.update(repository.getId(), repository);
//        return ResponseEntity.noContent().build();
//    }
//
//    public ResponseEntity<?> repositoryRemoveAccessType(String rootFullName, String name)
//    {
//        RepositoryDTO repositoryDTO = repositoryApiClient.getRepositoryByRootFullName(rootFullName).getBody();
//        AccessType accessType = accessTypeApiClient.getByName(name).getBody();
//        repositoryDTO.getAccessTypes().removeIf((x) -> {return x.getId() == accessType.getId();});
//
//        Repository repository = repositoryDTOMapper.toEntity(repositoryDTO);
//
//        repositoryApiClient.update(repository.getId(),repository);
//        return ResponseEntity.noContent().build();
//    }
}
