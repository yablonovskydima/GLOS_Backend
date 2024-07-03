package com.glos.accessservice.facade.chain;

import com.glos.accessservice.clients.FileApiClient;
import com.glos.accessservice.exeptions.HttpStatusCodeImplException;
import com.glos.accessservice.exeptions.ResourceNotFoundException;
import com.glos.accessservice.facade.chain.base.AccessHandler;
import com.glos.accessservice.facade.chain.base.AccessRequest;
import com.glos.accessservice.responseDTO.FileDTO;
import com.pathtools.NodeType;
import com.pathtools.Path;
import com.pathtools.pathnode.PathNode;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FileAccessHandler extends AccessHandler {

    private final FileApiClient fileApiClient;

    public FileAccessHandler(FileApiClient fileApiClient) {
        this.fileApiClient = fileApiClient;
    }

    @Override
    public boolean check(AccessRequest request) {
        super.check(request);
        final Map<String, Object> data = request.getData();
        final Path path = (Path)data.get("path");
        final PathNode node = path.getLast();
        if (!data.containsKey("accessTypes") && node.getType() == NodeType.FILE) {
            final ResponseEntity<FileDTO> response = fileApiClient.getByRootFullName(path.getPath());
            if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404))) {
                throw new ResourceNotFoundException("file not found");
            } else if (!response.getStatusCode().is2xxSuccessful()) {
                throw new HttpStatusCodeImplException(response.getStatusCode());
            }
            final FileDTO file = response.getBody();
            data.put("accessTypes", file.getAccessTypes());
        }
        return checkNext(request);
    }

}
