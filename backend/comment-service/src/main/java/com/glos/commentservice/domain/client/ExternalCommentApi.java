package com.glos.commentservice.domain.client;

import com.glos.commentservice.domain.DTO.Page;
import com.glos.commentservice.entities.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "comment")
public interface ExternalCommentApi {

    @GetMapping("/{id}")
    ResponseEntity<Comment> getById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<Comment> create (@RequestBody Comment comment);

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody Comment request);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteById(@PathVariable Long id);

    @GetMapping
    ResponseEntity<Page<Comment>> getAllByFilter(@SpringQueryMap Map<String, Object> filter);

    @GetMapping("/path/{rootFullName}")
    ResponseEntity<Page<Comment>> getAllCommentsByRootFullName(@PathVariable String rootFullName,
                                                               @SpringQueryMap Map<String, Object> filter);
}
