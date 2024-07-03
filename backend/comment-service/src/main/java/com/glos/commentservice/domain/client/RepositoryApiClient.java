package com.glos.commentservice.domain.client;

import com.glos.commentservice.domain.DTO.RepositoryDTO;
import com.glos.commentservice.entities.Repository;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "repositories")
public interface RepositoryApiClient
{
    @PutMapping("/{id}")
    ResponseEntity<?> updateRepository(@RequestBody Repository newRepository, @PathVariable("id") Long id);

    @GetMapping("/root-fullname/{rootFullName}")
    ResponseEntity<RepositoryDTO> getRepositoryByRootFullName(@PathVariable String rootFullName);

}
