package com.glos.notificationservice.domain.template.base;

import java.util.Map;

public interface AppendableMessageProps extends MessageProps {

    void putProp(String name, Object obj);
    void putProps(Map<String, Object> props);
    void removeProp(String name);

}
