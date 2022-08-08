package com.example.cloudstorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class Router {
    @Autowired
    Handler handler;

    @Bean
    public RouterFunction<ServerResponse> routes(Handler handler) {
        return RouterFunctions.route(GET("/init"), handler::init);
    }
}
