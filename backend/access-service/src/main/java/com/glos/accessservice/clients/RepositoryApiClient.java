package com.glos.accessservice.clients;

import com.glos.accessservice.entities.AccessType;
import com.glos.accessservice.entities.Repository;
import com.glos.accessservice.responseDTO.RepositoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "repositories")
public interface RepositoryApiClient
{
    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable Long id ,@RequestBody Repository repository);

    @PutMapping("/{id}/add-access-type")
    ResponseEntity<?> addAccessType(@PathVariable Long id, @RequestBody AccessType accessType);

    @PutMapping("/{id}/remove-access-type")
    ResponseEntity<?> removeAccessType(@PathVariable Long id, @RequestBody AccessType accessType);

    @GetMapping("/root-fullname/{rootFullName}")
    ResponseEntity<Repository> getRepositoryByRootFullName(@PathVariable String rootFullName);
}
