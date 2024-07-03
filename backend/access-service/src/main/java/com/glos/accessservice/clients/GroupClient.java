package com.glos.accessservice.clients;

import com.glos.accessservice.entities.Group;
import com.glos.accessservice.responseDTO.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "group")
public interface GroupClient {

    @GetMapping("/groups/filter")
    ResponseEntity<Page<Group>> getGroupsByFilters(@SpringQueryMap Map<String, Object> filter);

}
