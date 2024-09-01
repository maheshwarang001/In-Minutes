package com.swipe.apigateway.filter;
import com.google.common.net.HttpHeaders;
import com.swipe.apigateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;



@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {


    @Autowired
    JwtUtil jwtUtil;

    public AuthFilter() {
        super(Config.class);
    }

    @Autowired
    RouteValidator routeValidator;



    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {

            if (routeValidator.isSecured.test(exchange.getRequest())) {

                // Check if the header contains the token
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return handleError(exchange, "MISSING AUTH HEADER", HttpStatus.UNAUTHORIZED);
                }

                String header = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                if (header != null && header.startsWith("Bearer")) {
                    String token = header.substring(7);

                    try {
                        // Validate the token


                        boolean jwt =  jwtUtil.validateToken(token);

                        if (jwt == false) {
                            System.out.println("INVALID TOKEN " + token);
                            return handleError(exchange, "INVALID TOKEN", HttpStatus.UNAUTHORIZED);
                        }

                        String userId = jwtUtil.extractUserId(token);
                        exchange.getRequest().mutate().header("X-User-ID", userId);

                    } catch (Exception e) {
                        return handleError(exchange, "INTERNAL SERVER ERROR" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                } else {
                    return handleError(exchange, "Invalid request", HttpStatus.BAD_REQUEST);
                }
            }

            return chain.filter(exchange);
        }));
    }

    private Mono<Void> handleError(ServerWebExchange exchange, String message, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "text/plain");
        return response.writeWith(Mono.just(response.bufferFactory().wrap(message.getBytes())));
    }
    public static class Config {

    }


}