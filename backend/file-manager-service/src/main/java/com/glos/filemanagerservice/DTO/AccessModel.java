package com.glos.filemanagerservice.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccessModel {

    private String type;
    private String name;
    private String access;
    private List<String> elements;

    public AccessModel() {
        this.elements = new ArrayList<>();
    }

    public AccessModel(String type, String name, String access, List<String> elements) {
        this.type = type;
        this.name = name;
        this.access = access;
        this.elements = elements;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public List<String> getElements() {
        return elements;
    }

    public void setElements(List<String> elements) {
        this.elements = elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessModel that = (AccessModel) o;
        return Objects.equals(type, that.type) && Objects.equals(name, that.name) && Objects.equals(access, that.access) && Objects.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, access, elements);
    }
}