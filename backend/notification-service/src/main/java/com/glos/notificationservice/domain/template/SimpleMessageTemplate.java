package com.glos.notificationservice.domain.template;

import com.glos.notificationservice.domain.template.base.MessageProps;
import com.glos.notificationservice.domain.template.base.MessageTemplate;
import com.glos.notificationservice.domain.template.base.TemplateNames;
import org.thymeleaf.context.Context;

public class SimpleMessageTemplate implements MessageTemplate {

    private final MessageProps properties;
    private final Context context;


    public SimpleMessageTemplate(MessageProps props) {
        this.properties = props;
        this.context = new Context();
        context.setVariables(props.asMap());
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public String getViewName() {
        return TemplateNames.NOTIFICATION.template();
    }

    @Override
    public MessageProps getProperties() {
        return properties;
    }
}
