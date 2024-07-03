package com.glos.filemanagerservice.facade.chain.base;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class Request<T> {

    private T body;
    private Map<String, Object> data;
    private Predicate<T> predicate;

    public Request() {
        this.data = new HashMap<>();
        this.predicate = x -> false;
    }

    public Request(T body, Predicate<T> predicate) {
        this(body, predicate, new HashMap<>());
    }

    public Request(T body, Predicate<T> predicate, Map<String, Object> data) {
        this.body = body;
        this.predicate = predicate;
        this.data = data;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Predicate<T> getPredicate() {
        return predicate;
    }

    public boolean checkBody() {
        return predicate.test(body);
    }

    public void setPredicate(Predicate<T> predicate) {
        this.predicate = predicate;
    }
}
