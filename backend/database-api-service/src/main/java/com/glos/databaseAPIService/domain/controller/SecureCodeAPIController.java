package com.glos.databaseAPIService.domain.controller;


import com.glos.databaseAPIService.domain.entities.SecureCode;
import com.glos.databaseAPIService.domain.service.SecureCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/secure-codes")
public class SecureCodeAPIController
{
    private final SecureCodeService service;

    @Autowired
    public SecureCodeAPIController(SecureCodeService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecureCode> getSecureCodeById(@PathVariable Long id)
    {
        return ResponseEntity.of(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> createSecureCode(@RequestBody SecureCode secureCode, UriComponentsBuilder uriBuilder)
    {
        SecureCode s = service.create(secureCode);
        return ResponseEntity.created(
                uriBuilder.path("/secure-codes/{id}")
                        .build(s.getId())).body(s);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSecureCode(@PathVariable Long id)
    {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSecureCode(@PathVariable Long id, @RequestBody SecureCode newSecureCode)
    {
        service.update(id, newSecureCode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/path/{path}")
    public ResponseEntity<SecureCode> getByReceiverAndResourcePath(@PathVariable("path") String path)
    {
        System.out.println(path);
        return ResponseEntity.of(service.getByResourcePath(path));
    }

    @GetMapping
    public ResponseEntity<Page<SecureCode>> getAllSecureCode(@ModelAttribute SecureCode filter,
                                                             @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable)
    {
        return ResponseEntity.ok(service.getAllByFilter(filter, pageable));
    }
}
