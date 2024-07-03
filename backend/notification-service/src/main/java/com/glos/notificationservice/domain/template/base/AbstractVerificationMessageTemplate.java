package com.glos.notificationservice.domain.template.base;

import org.thymeleaf.context.Context;

public abstract class AbstractVerificationMessageTemplate implements MessageTemplate {

    protected Context context;
    protected String viewName;
    protected MessageProps properties;

    public AbstractVerificationMessageTemplate(Context context, String viewName, MessageProps properties) {
        this.context = context;
        this.viewName = viewName;
        this.properties = properties;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public String getViewName() {
        return viewName;
    }

    @Override
    public MessageProps getProperties() {
        return properties;
    }
}
