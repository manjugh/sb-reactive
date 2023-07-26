package com.example.reactive.functional.handler;

import com.example.reactive.functional.dto.PostDto;
import com.example.reactive.functional.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@AllArgsConstructor
public class PostHandler {
    public static final Mono<ServerResponse> NOT_FOUND = ServerResponse.notFound().build();
    private final PostService postService;

    public Mono<ServerResponse> savePost(final ServerRequest serverRequest) {
        Mono<PostDto> postDtoMono = serverRequest.bodyToMono(PostDto.class);
        return postDtoMono.flatMap(postDto -> ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(postService.create(postDto), PostDto.class))
                .switchIfEmpty(NOT_FOUND);
    }

    public Mono<ServerResponse> readPost(final ServerRequest serverRequest) {
        String postId = serverRequest.pathVariable("postId");
        Mono<PostDto> post = postService.getPost(postId);
        return postService.getPost(postId)
                .flatMap(postDto -> ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(fromValue(postDto)))
                .switchIfEmpty(NOT_FOUND);
    }
}
