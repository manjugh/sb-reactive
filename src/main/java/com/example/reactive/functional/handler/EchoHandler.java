package com.example.reactive.functional.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class EchoHandler {

    public Mono<ServerResponse> echo(final ServerRequest serverRequest) {
        /*try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        return ServerResponse.ok().body(fromValue(serverRequest.queryParam("name")));
    }
}
