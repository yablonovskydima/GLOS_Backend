package com.glos.databaseAPIService.domain.controller;

import com.glos.databaseAPIService.domain.entities.AccessType;
import com.glos.databaseAPIService.domain.service.AccessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author Mykola Melnyk
 */
@RestController
@RequestMapping("/access-types")
public class AccessTypeAPIController {

    private final AccessTypeService accessTypeService;

    @Autowired
    public AccessTypeAPIController(AccessTypeService accessTypeService) {
        this.accessTypeService = accessTypeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccessType>> getAll() {
        return ResponseEntity.ok(accessTypeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessType> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.of(accessTypeService.getById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<AccessType> getByName(
            @PathVariable String name
    ) {
        return ResponseEntity.of(accessTypeService.getByName(name));
    }

    @PostMapping
    public ResponseEntity<AccessType> create(
            @RequestBody AccessType request,
            UriComponentsBuilder uriBuilder
    ) {
        AccessType created = accessTypeService.create(request);
        return ResponseEntity.created(
                    uriBuilder.path("/access-types/{id}")
                        .build(created.getId())
                ).body(created);
    }

    @PutMapping("/ensure/{name}")
    public ResponseEntity<AccessType> ensure(@PathVariable String name,
                                             UriComponentsBuilder uriBuilder) {
        Map.Entry<AccessType, Boolean> accessTypeEntry = accessTypeService.ensure(name);
        if (!accessTypeEntry.getValue()) {
            return ResponseEntity.ok(accessTypeEntry.getKey());
        }
        final AccessType accessType = accessTypeEntry.getKey();
        return ResponseEntity.created(
                uriBuilder.path("/access-types/name/{name}")
                .build(accessType.getName())
        ).body(accessType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccessType> update(
            @PathVariable Long id,
            @RequestBody AccessType request
    ) {
        accessTypeService.update(id, request);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable Long id
    ) {
        accessTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<AccessType>> getByFilter(
            @ModelAttribute AccessType accessType,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    )
    {
        return ResponseEntity.ok(accessTypeService.getPageByFilter(accessType, pageable));
    }
}
