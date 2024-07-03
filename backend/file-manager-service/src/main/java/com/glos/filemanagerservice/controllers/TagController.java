package com.glos.filemanagerservice.controllers;

import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.entities.Tag;
import com.glos.filemanagerservice.facade.TagFacade;
import com.glos.filemanagerservice.requestFilters.TagRequestFilter;
import com.pathtools.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
public class TagController
{
    private final TagFacade tagFacade;

    public TagController(TagFacade tagFacade) {
        this.tagFacade = tagFacade;
    }

    @GetMapping("/tags")
    public ResponseEntity<Page<String>> getTags(@ModelAttribute Tag tag,
                                             @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                             @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                             @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort)
    {
        TagRequestFilter requestFilter = new TagRequestFilter(tag.getId(), tag.getName(), page, size, sort);
        Page<Tag> tagsPage = tagFacade.getTagsByFilter(requestFilter);
        return ResponseEntity.ok(tagsPage.map(Tag::getName));
    }

    @PutMapping("/tags/{name}")
    public ResponseEntity<?> putTag(@PathVariable String name) {
        return tagFacade.putTag(name);
    }

    @DeleteMapping("/tags/{name}")
    public ResponseEntity<?> deleteTag(@PathVariable String name) {
        return tagFacade.deleteTag(name);
    }

    @PutMapping("/tags/{rootFullName}/add-tag/{tagName}")
    public ResponseEntity<?> addTag(
            @PathVariable String rootFullName,
            @PathVariable String tagName) {
        final Path path = Path.builder(rootFullName).build();
        return tagFacade.addTag(path, tagName);
    }

    @DeleteMapping("/tags/{rootFullName}/remove-tag/{tagName}")
    public ResponseEntity<?> removeTag(
            @PathVariable String rootFullName,
            @PathVariable String tagName) {
        final Path path = Path.builder(rootFullName).build();
        return tagFacade.removeTag(path, tagName);
    }

}

