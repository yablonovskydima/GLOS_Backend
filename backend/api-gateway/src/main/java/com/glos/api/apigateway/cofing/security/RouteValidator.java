package com.glos.api.apigateway.cofing.security;

import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;
import java.util.function.Predicate;

public class RouteValidator {

    public static final List<String> OPEN_API_ENDPOINTS = List.of(
            "/auth/register",
            "/auth/login",
            "/eureka"
    );

    public static final Predicate<ServerHttpRequest> isSecured =
            request -> OPEN_API_ENDPOINTS
                    .stream().noneMatch(uri -> request.getURI().getPath().contains(uri));

}