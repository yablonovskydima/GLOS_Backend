package com.glos.filemanagerservice.controllers;

import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.DTO.RepositoryDTO;
import com.glos.filemanagerservice.facade.FeedApiFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed")
public class FeedController
{
    private final FeedApiFacade feedApiFacade;

    public FeedController(FeedApiFacade feedApiFacade) {
        this.feedApiFacade = feedApiFacade;
    }

    @GetMapping
    public ResponseEntity<Page<RepositoryDTO>> getPublicRepositories(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                                     @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                                     @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort)
    {
        return ResponseEntity.ok(feedApiFacade.getPublicRepos(page, size, sort).getBody());
    }
}
