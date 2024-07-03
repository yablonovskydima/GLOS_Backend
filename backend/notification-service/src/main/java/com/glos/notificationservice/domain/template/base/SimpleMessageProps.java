package com.glos.notificationservice.domain.template.base;

import com.glos.notificationservice.domain.template.base.AppendableMessageProps;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SimpleMessageProps implements AppendableMessageProps {

    private final Map<String, Object> props;
    private Map<String, Object> unmodifiableProps;
    private boolean isModified;

    public SimpleMessageProps() {
        this.props = new HashMap<>();
    }

    public SimpleMessageProps(Map<String, Object> props) {
        this.props = new HashMap<>(props);
        this.unmodifiableProps = null;
        this.isModified = true;
    }

    @Override
    public void putProp(String name, Object obj) {
        props.put(name, obj);
        this.isModified = true;
    }

    @Override
    public void putProps(Map<String, Object> props) {
        props.forEach(this::putProp);
        this.isModified = true;
    }

    @Override
    public void removeProp(String name) {
        props.remove(name);
        this.isModified = true;
    }

    @Override
    public Object getValue(String name) {
        return props.get(name);
    }

    @Override
    public <T> T getValue(String name, Class<T> type) {
        return (T) props.get(name);
    }

    @Override
    public Map<String, Object> asMap() {
        if (isModified) {
            unmodifiableProps = Collections.unmodifiableMap(props);
            isModified = false;
        }
        return unmodifiableProps;
    }
}
