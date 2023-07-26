package com.example.reactive.functional.service;

import com.example.reactive.functional.dto.PostDto;
import com.example.reactive.functional.repo.Post;
import com.example.reactive.functional.repo.PostReactiveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class PostService {
    private final PostReactiveRepository postReactiveRepository;

    public Mono<PostDto> create(final PostDto postDto) {
        Post post = Post.builder().title(postDto.getTitle()).description(postDto.getDescription())
                .created_at(LocalDateTime.now())
                .build();
        return postReactiveRepository.save(post).map(post1 -> {
            postDto.setId(post1.getID());
            return postDto;
        });

    }

    public Mono<PostDto> getPost(String postId) {
        return postReactiveRepository
                .findById(Long.valueOf(postId))
                .map(post -> PostDto.builder().title(post.getTitle()).id(post.getID()).description(post.getDescription()).build())
                .switchIfEmpty(Mono.error(new RuntimeException("User Not Found")));
    }

    public Flux<PostDto> listPosts() {
        return postReactiveRepository.findAll()
                .log()
                .delayElements(Duration.ofMillis(2000))
                //.doOnNext(post -> "sending post"+post.getID())
                .map(post -> PostDto.builder().title(post.getTitle()).id(post.getID()).description(post.getDescription()).build());

    }
}
