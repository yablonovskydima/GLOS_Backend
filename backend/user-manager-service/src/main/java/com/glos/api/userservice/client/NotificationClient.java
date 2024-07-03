package com.glos.api.userservice.client;

import com.glos.api.userservice.responseDTO.MessageVerificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification")
public interface NotificationClient {

    @PostMapping("/email/send/{action}")
    ResponseEntity<?> sendTemplate(@PathVariable String action, @RequestBody MessageVerificationDTO messageData);

}
