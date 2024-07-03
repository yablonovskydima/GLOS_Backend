package com.glos.notificationservice.domain.template.base;

import java.util.Map;

public interface MessageProps {

    static AppendableMessageProps simple() {
        return new SimpleMessageProps();
    }

    static AppendableMessageProps of(Map<String, Object> props){
        return new SimpleMessageProps(props);
    }

    Object getValue(String name);
    <T> T getValue(String name, Class<T> type);
    Map<String, Object> asMap();
}
