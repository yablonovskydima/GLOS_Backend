package com.glos.filemanagerservice.clients;

import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.entities.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@FeignClient(name = "tags")
public interface TagClient
{
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTag(@PathVariable Long id);

    @GetMapping("/name/{name}")
    ResponseEntity<Tag> getTagByName(@PathVariable String name);

    @GetMapping
    ResponseEntity<Page<Tag>> getByFilter(@SpringQueryMap Map<String, Object> filter);

    @PutMapping("/ensure/{tagName}")
    ResponseEntity<Tag> ensureTag(@PathVariable String tagName);
}
