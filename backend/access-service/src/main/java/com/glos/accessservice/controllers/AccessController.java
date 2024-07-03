package com.glos.accessservice.controllers;

import com.glos.accessservice.facade.AccessFacade;
import com.glos.accessservice.facade.chain.base.AccessRequest;
import com.glos.accessservice.requestDTO.AccessModel;
import com.glos.accessservice.responseDTO.Page;
import com.glos.accessservice.responseDTO.Pageable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccessController {

    private final AccessFacade accessFacade;

    public AccessController(AccessFacade accessFacade) {
        this.accessFacade = accessFacade;
    }

    @GetMapping("/access/{access}/{rootFullName}/available/{username}")
    public ResponseEntity<?> isAvailable(
            @PathVariable("access")
                @NotEmpty(message = "access is require field")
                @Pattern(regexp = "(read|readwrite|r|rw)", message = "Invalid access type")
                String access,
            @PathVariable("rootFullName")
                @NotEmpty(message = "rootFullName is require field")
                @Pattern(regexp = "^(\\$[^\\$\\#\\+\\%]+)((\\%|\\#)[^\\$\\#\\+\\%\\.]+.[a-z]{2,6})*((\\$|\\#|\\+|\\%)[^\\$\\#\\+\\%]+)*$",
                        message = "Invalid path format")
                String rootFullName,
            @PathVariable("username")
                @NotEmpty(message = "username is require field")
                String username
    ) {
        AccessRequest request = new AccessRequest();
        if ("read".equals(access)) {
            request.setReadOnly(true);
        } else if (List.of("write", "readwrite").contains(access)) {
            request.setReadOnly(false);
        } else {
            return ResponseEntity.badRequest().build();
        }
        request.setPath(rootFullName);
        request.setUsername(username);
        return accessFacade.available(request);
    }

    @PutMapping("/access/{rootFullName}/add-access")
    public ResponseEntity<?> addAccess(
            @PathVariable("rootFullName")
                @NotEmpty(message = "rootFullName is require field")
                @Pattern(regexp = "^(\\$[^\\$\\#\\+\\%]+)((\\%|\\#)[^\\$\\#\\+\\%\\.]+.[a-z]{2,6})*((\\$|\\#|\\+|\\%)[^\\$\\#\\+\\%]+)*$",
                    message = "Invalid path format")
                String rootFullName,
            @RequestBody @Validated AccessModel model)
    {
        accessFacade.addAccess(rootFullName, model);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/access/{rootFullName}/remove-access")
    public ResponseEntity<?> removeAccess(
            @PathVariable("rootFullName")
            @NotEmpty(message = "rootFullName is require field")
            @Pattern(regexp = "^(\\$[^\\$\\#\\+\\%]+)((\\%|\\#)[^\\$\\#\\+\\%\\.]+.[a-z]{2,6})*((\\$|\\#|\\+|\\%)[^\\$\\#\\+\\%]+)*$",
                    message = "Invalid path format")
                String rootFullName,
            @RequestBody @Validated AccessModel model)
    {

        accessFacade.removeAccess(rootFullName, model);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/access/{rootFullName}/list")
    public ResponseEntity<Page<AccessModel>> getAccesses(
            @PathVariable
                @NotEmpty(message = "rootFullName is require field")
                @Pattern(regexp = "^(\\$[^\\$\\#\\+\\%]+)((\\%|\\#)[^\\$\\#\\+\\%\\.]+.[a-z]{2,6})*((\\$|\\#|\\+|\\%)[^\\$\\#\\+\\%]+)*$",
                    message = "Invalid path format")
                String rootFullName,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "sort", required = false, defaultValue = "name,asc") String sort
    ) {
        Pageable pageable = new Pageable(page, size, sort);
        return ResponseEntity.ok(accessFacade.accessPage(rootFullName, pageable));
    }
}
