package com.glos.databaseAPIService.domain.controller;


import com.glos.databaseAPIService.domain.entities.Tag;
import com.glos.databaseAPIService.domain.service.TagService;
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
 * 	@author - yablonovskydima
 */
@RestController
@RequestMapping("/tags")
public class TagAPIController
{
    private final TagService tagService;

    @Autowired
    public TagAPIController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id)
    {
        return ResponseEntity.of(tagService.getById(id));
    }

    @PutMapping("/ensure/{tagName}")
    public ResponseEntity<Tag> ensureTag(@PathVariable String tagName, UriComponentsBuilder uriBuilder) {
        Map.Entry<Tag, Boolean> pair =  tagService.ensure(tagName);
        if (pair.getValue()) {
            return ResponseEntity.created(uriBuilder.path("/tags/name/{tagName}")
                    .build(tagName)).body(pair.getKey());
        } else {
            return ResponseEntity.ok(pair.getKey());
        }
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag, UriComponentsBuilder uriBuilder)
    {
        Tag t = tagService.create(tag);
        return ResponseEntity.created(
                uriBuilder.path("/tags/{id}")
                        .build(t.getId())).body(t);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable Long id)
    {
        tagService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTag(@PathVariable("id") Long id ,@RequestBody Tag newTag)
    {
        tagService.update(id, newTag);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Tag> getTagByName(@PathVariable String name)
    {
        return ResponseEntity.of(tagService.getByName(name));
    }

    @GetMapping
    public ResponseEntity<Page<Tag>> getByFilter(@ModelAttribute Tag filter,
                                                 @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable)
    {
        return ResponseEntity.ok(tagService.findAllByFilter(filter, pageable));
    }
}
