package com.example.reactive.functional.controller;

import com.example.reactive.functional.dto.PostDto;
import com.example.reactive.functional.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping
    public Mono<PostDto> savePost(@RequestBody final Mono<PostDto> postDto) {
        return postDto.map(postDto1 -> postService.create(postDto1))
                .flatMap(postDtoMono -> postDtoMono);

    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<PostDto> listPosts() {
        return postService.listPosts();
    }

    @GetMapping("/{id}")
    public Mono<PostDto> getPost(@PathVariable("id") String postId) {
        return postService.getPost(postId);
    }
}
