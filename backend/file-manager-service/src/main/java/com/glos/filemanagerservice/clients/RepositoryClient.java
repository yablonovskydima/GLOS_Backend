package com.glos.filemanagerservice.clients;

import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.DTO.RepositoryDTO;
import com.glos.filemanagerservice.entities.Repository;
import com.glos.filemanagerservice.requestFilters.RepositoryRequestFilter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "repositories")
public interface RepositoryClient
{
    @GetMapping("/{id}")
    ResponseEntity<Repository> getRepositoryById(@PathVariable Long id);
    @PostMapping
    ResponseEntity<Repository> createRepository(@RequestBody Repository repository);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteRepository(@PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<?> updateRepository(@RequestBody Repository newRepository, @PathVariable("id") Long id);

    @GetMapping("/owner-id/{ownerId}")
    ResponseEntity<List<Repository>> getRepositoriesByOwnerId(@PathVariable Long ownerId);

    @PutMapping
    ResponseEntity<Page<Repository>> getRepositoriesByFilter(@ModelAttribute Repository repository, @SpringQueryMap Map<String, Object> map);

    @GetMapping("/root-fullname/{rootFullName}")
    ResponseEntity<Repository> getRepositoryByRootFullName(@PathVariable String rootFullName);

}
