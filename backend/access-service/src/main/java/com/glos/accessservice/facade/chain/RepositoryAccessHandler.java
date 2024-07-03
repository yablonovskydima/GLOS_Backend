package com.glos.accessservice.facade.chain;

import com.glos.accessservice.clients.RepositoryApiClient;
import com.glos.accessservice.entities.Repository;
import com.glos.accessservice.exeptions.HttpStatusCodeImplException;
import com.glos.accessservice.exeptions.ResourceNotFoundException;
import com.glos.accessservice.facade.chain.base.AccessHandler;
import com.glos.accessservice.facade.chain.base.AccessRequest;
import com.glos.accessservice.responseDTO.RepositoryDTO;
import com.glos.accessservice.responseMappers.AccessModelMapper;
import com.pathtools.NodeType;
import com.pathtools.Path;
import com.pathtools.pathnode.PathNode;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.nio.file.AccessMode;
import java.util.Map;

@Component
public class RepositoryAccessHandler extends AccessHandler {

    private final RepositoryApiClient repositoryApiClient;
    private final AccessModelMapper accessModelMapper;

    public RepositoryAccessHandler(RepositoryApiClient repositoryApiClient, AccessModelMapper accessModelMapper) {
        this.repositoryApiClient = repositoryApiClient;
        this.accessModelMapper = accessModelMapper;
    }

    @Override
    public boolean check(AccessRequest request) {
        super.check(request);
        final Map<String, Object> data = request.getData();
        final Path path = (Path)data.get("path");
        final PathNode node = path.getLast();
        if (!data.containsKey("accessTypes") && node.getType() == NodeType.REPOSITORY) {
            final ResponseEntity<Repository> response = repositoryApiClient.getRepositoryByRootFullName(path.getPath());
            if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404))) {
                throw new ResourceNotFoundException("repository not found");
            } else if (!response.getStatusCode().is2xxSuccessful()) {
                throw new HttpStatusCodeImplException(response.getStatusCode());
            }
            final Repository repository = response.getBody();
            data.put("accessTypes", repository.getAccessTypes());
        }
        return checkNext(request);
    }

}
