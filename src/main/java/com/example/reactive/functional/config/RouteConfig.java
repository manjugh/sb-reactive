package com.example.reactive.functional.config;

import com.example.reactive.functional.handler.EchoHandler;
import com.example.reactive.functional.handler.PostHandler;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class RouteConfig {


    @Bean
    public WebProperties.Resources webPropResources() {
        return new WebProperties.Resources();
    }

    @Bean
    public RouterFunction<ServerResponse> echoRouter(final EchoHandler echoHandler) {
        return RouterFunctions.route(GET("/echo"), echoHandler::echo);
    }

    @Bean
    public RouterFunction<ServerResponse> routes(final PostHandler postHandler) {
        return RouterFunctions.route(GET("/blog/{postId}"), postHandler::readPost)
                .andRoute(POST("/blog"), postHandler::savePost);

    }
}
