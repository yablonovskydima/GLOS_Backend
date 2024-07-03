package com.glos.notificationservice.domain.template.base;

import org.thymeleaf.context.Context;

public interface MessageTemplate {
    MessageTemplate EMPTY = new MessageTemplate() {
        @Override
        public Context getContext() {
            return null;
        }

        @Override
        public String getViewName() {
            return null;
        }

        @Override
        public MessageProps getProperties() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }
    };



    Context getContext();
    String getViewName();
    MessageProps getProperties();
    default boolean hasView() {
        return getViewName() != null;
    }
    default boolean hasContext() {
        return getContext() != null;
    }
    default boolean hasProperties() {
        return getProperties() != null;
    }
    default boolean isEmpty() {
        return !hasContext() && !hasProperties() && !hasView();
    }
}
