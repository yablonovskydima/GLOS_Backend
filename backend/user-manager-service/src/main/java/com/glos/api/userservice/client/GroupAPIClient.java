package com.glos.api.userservice.client;

import com.glos.api.userservice.entities.Group;
import com.glos.api.userservice.responseDTO.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "group")
public interface GroupAPIClient
{
    @GetMapping("/{id}")
    ResponseEntity<Group> getGroupById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<Group> createGroup(@RequestBody Group group);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteGroup(@PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<Group> updateGroup(@PathVariable Long id, @RequestBody Group newGroup);

    @GetMapping
    ResponseEntity<Page<Group>> getAllGroups(@SpringQueryMap Map<String, Object> filter);

    @GetMapping("/filter")
    ResponseEntity<Page<Group>> getGroupsByFilters(@SpringQueryMap Map<String, Object> filter);
}
