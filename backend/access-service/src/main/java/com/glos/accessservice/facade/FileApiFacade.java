package com.glos.accessservice.facade;

import com.glos.accessservice.clients.AccessTypeApiClient;
import com.glos.accessservice.clients.FileApiClient;
import com.glos.accessservice.entities.AccessType;
import com.glos.accessservice.entities.File;
import com.glos.accessservice.requestDTO.AccessModel;
import com.glos.accessservice.responseDTO.FileDTO;
import com.glos.accessservice.responseMappers.AccessModelMapper;
import com.glos.accessservice.responseMappers.FileDTOMapper;
import com.pathtools.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FileApiFacade
{
    private final FileApiClient fileApiClient;
    private final AccessTypeApiClient accessTypeApiClient;
    private final FileDTOMapper fileDTOMapper;
    private final AccessModelMapper accessModelMapper;

    public FileApiFacade(FileApiClient fileApiClient,
                         AccessTypeApiClient accessTypeApiClient,
                         FileDTOMapper fileDTOMapper,
                         AccessModelMapper accessModelMapper) {
        this.fileApiClient = fileApiClient;
        this.accessTypeApiClient = accessTypeApiClient;
        this.fileDTOMapper = fileDTOMapper;
        this.accessModelMapper = accessModelMapper;
    }

//    public ResponseEntity<?> fileAppendAccessType(String rootFullName, AccessModel model)
//    {
//        AccessType type = accessModelMapper.toEntity(model);
//        FileDTO fileDTO = fileApiClient.getByRootFullName(rootFullName).getBody();
//        AccessType accessType = accessTypeApiClient.getByName(type.getName()).getBody();
//        fileDTO.getAccessTypes().add(accessType);
//
//        File file = fileDTOMapper.toEntity(fileDTO);
//
//        fileApiClient.update(file.getId(), file);
//        return ResponseEntity.noContent().build();
//    }
//
//    public ResponseEntity<?> fileRemoveAccessType(String rootFullName, AccessModel model)
//    {
//        Path path = Path.builder(rootFullName).build();
//        AccessType type = accessModelMapper.toEntity(model);
//        FileDTO fileDTO = fileApiClient.getByRootFullName(path.getPath()).getBody();
//        AccessType accessType = accessTypeApiClient.getByName(type.getName()).getBody();
//        fileDTO.getAccessTypes().removeIf((x) -> x.getId().equals(accessType.getId()));
//
//        File file = fileDTOMapper.toEntity(fileDTO);
//
//        fileApiClient.update(file.getId(), file);
//        return ResponseEntity.noContent().build();
//    }
}
