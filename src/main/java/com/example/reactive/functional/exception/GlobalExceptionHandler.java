package com.example.reactive.functional.exception;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageReaders(serverCodecConfigurer.getReaders());
        this.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::handleTheError);
    }

    private <T extends ServerResponse> Mono<ServerResponse> handleTheError(ServerRequest serverRequest) {

        Map<String, Object> errorAttributes = this.getErrorAttributes(serverRequest, ErrorAttributeOptions.defaults());
        var errorResponse = new TestErrorResponse(errorAttributes.get("code"), (String) errorAttributes.get("message"));
        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorResponse));

    }


    record TestErrorResponse(Object errorCode, Object errorMessage){}


}
