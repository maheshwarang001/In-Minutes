package com.swipe.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/finzo_auth/v1/res/request-otp-signup",
            "/finzo_auth/v1/res/register-user",
            "/finzo_auth/v1/res/generate-token",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri->
                            request
                                    .getURI()
                                    .getPath()
                                    .contains(uri));

}
