package com.glos.notificationservice.controller;

import com.glos.notificationservice.domain.DTO.MessageHtmlDTO;
import com.glos.notificationservice.domain.DTO.SimpleMessageDTO;
import com.glos.notificationservice.domain.DTO.MessageVerificationDTO;
import com.glos.notificationservice.domain.template.*;
import com.glos.notificationservice.domain.template.base.AppendableMessageProps;
import com.glos.notificationservice.domain.template.base.MessageProps;
import com.glos.notificationservice.domain.template.base.MessageTemplate;
import com.glos.notificationservice.domain.template.base.TemplateNames;
import com.glos.notificationservice.manager.EmailManager;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailController
{

    private final EmailManager emailManager;


    public EmailController(EmailManager emailManager)
    {
        this.emailManager = emailManager;
    }


    @GetMapping("/templates")
    public ResponseEntity<List<String>> getAllTemplates() {
        return ResponseEntity.ok(Arrays.stream(TemplateNames.values()).map(Enum::toString).toList());
    }

    @PostMapping("/send/{action}")
    public ResponseEntity<?> sendMessage(
            @PathVariable String action,
            @RequestBody MessageVerificationDTO messageDTO
    ) throws MessagingException {
        final TemplateNames tn = TemplateNames.valueOfIgnoreCase(action);
        final AppendableMessageProps props = MessageProps.simple();
        props.putProps(messageDTO.getData());
        props.putProp("to", messageDTO.getTo());
        props.putProp("subject", messageDTO.getSubject());
        props.putProp("text", messageDTO.getText());
        final MessageTemplate messageTemplate = tn.messageTemplate().apply(props);
        emailManager.send(messageTemplate);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send-message")
    public HttpStatus sendMessage(
            @RequestBody SimpleMessageDTO messageDTO
    ) throws MessagingException {
        final AppendableMessageProps props = MessageProps.simple();
        props.putProp("to", messageDTO.getTo());
        props.putProp("subject", messageDTO.getSubject());
        props.putProp("text", messageDTO.getText());
        final MessageTemplate messageTemplate = new SimpleMessageTemplate(props);
        emailManager.send(messageTemplate);
        return HttpStatus.OK;
    }

    @PostMapping("/send-html")
    public HttpStatus sendHtml(
            @RequestBody MessageHtmlDTO messageHtmlDTO
    ) throws MessagingException {
        final AppendableMessageProps props = MessageProps.simple();
        props.putProp("to", messageHtmlDTO.getTo());
        props.putProp("subject", messageHtmlDTO.getSubject());
        props.putProp("html", messageHtmlDTO.getPlainHtml());
        final MessageTemplate messageTemplate = new HtmlMessageTemplate(props);
        emailManager.send(messageTemplate);
        return HttpStatus.OK;
    }

}
