package com.glos.accessservice.clients;

import com.glos.accessservice.entities.AccessType;
import com.glos.accessservice.responseDTO.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "accessTypes")
public interface AccessTypeApiClient
{
    @GetMapping("/{id}")
    ResponseEntity<AccessType> getById(@PathVariable Long id);

    @GetMapping("/name/{name}")
    ResponseEntity<AccessType> getByName(@PathVariable String name);

    @PutMapping("/ensure/{name}")
    ResponseEntity<AccessType> ensure(@PathVariable String name);

    @PostMapping
    ResponseEntity<AccessType> create(@RequestBody AccessType request);

    @PutMapping("/{id}")
    ResponseEntity<AccessType> update(@PathVariable Long id, @RequestBody AccessType request);


    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteById(@PathVariable Long id);

    @GetMapping
    ResponseEntity<Page<AccessType>> getByFilter(@SpringQueryMap Map<String, Object> filter);
}
