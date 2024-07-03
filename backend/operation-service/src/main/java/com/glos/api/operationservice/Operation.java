package com.glos.api.operationservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Document("operations")
@CompoundIndexes({
        @CompoundIndex(name = "uq_operations_user_action", unique = true, def = "{ 'data.username': 1, 'action': 1 }")
})
public class Operation {

    @Id
    private UUID id;

    @Field(name = "code")
    @Indexed(unique = true)
    private String code;

    @Field(name = "action")
    private String action;

    @Field(name = "data")
    private Map<String, String> data;

    @Field(name = "createdDatetime")
    private LocalDateTime createdDatetime;

    @Field(name = "expiredDatetime")
    private LocalDateTime expiredDatetime;

    @Field(name = "executionDatetime")
    private LocalDateTime executionDatetime;

    public Operation() {

    }

    public Operation(UUID id, String code, String action, Map<String, String> data, LocalDateTime createdDatetime, LocalDateTime expiredDatetime, LocalDateTime executionDatetime) {
        this.id = id;
        this.code = code;
        this.action = action;
        this.data = data;
        this.createdDatetime = createdDatetime;
        this.expiredDatetime = expiredDatetime;
        this.executionDatetime = executionDatetime;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public LocalDateTime getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(LocalDateTime createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public LocalDateTime getExpiredDatetime() {
        return expiredDatetime;
    }

    public void setExpiredDatetime(LocalDateTime expiredDatetime) {
        this.expiredDatetime = expiredDatetime;
    }

    public LocalDateTime getExecutionDatetime() {
        return executionDatetime;
    }

    public void setExecutionDatetime(LocalDateTime executionDatetime) {
        this.executionDatetime = executionDatetime;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredDatetime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(id, operation.id) && Objects.equals(code, operation.code) && Objects.equals(action, operation.action) && Objects.equals(data, operation.data) && Objects.equals(createdDatetime, operation.createdDatetime) && Objects.equals(expiredDatetime, operation.expiredDatetime) && Objects.equals(executionDatetime, operation.executionDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, action, data, createdDatetime, expiredDatetime, executionDatetime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("{");
        builder.append("id").append(id);
        builder.append("code").append(code);
        builder.append("action").append(action);
        builder.append("data").append(data);
        builder.append("createdDatetime").append(createdDatetime);
        builder.append("expiredDatetime").append(expiredDatetime);
        builder.append("executionDatetime").append(executionDatetime);
        return builder.append("}").toString();
    }
}
