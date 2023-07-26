package com.example.reactive.functional;

import com.example.reactive.functional.controller.PostController;
import com.example.reactive.functional.dto.PostDto;
import com.example.reactive.functional.service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

//@SpringBootTest
@WebFluxTest(PostController.class)
@Import(TestConfig.class)
public class ReactiveRestApplicationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PostService postService;

    @Test
    void savePostTest() {
        PostDto postDto = PostDto.builder().description("Test desc")
                .title("Test 1").build();
        BDDMockito.when(postService.create(Mockito.any(PostDto.class))).thenReturn(Mono.just(postDto));
        webTestClient.post().uri("/posts")
                .body(Mono.just(postDto), PostDto.class)
                .exchange()
                .expectStatus()
                .isOk();

    }

    @Test
    void listPostsTest() {
        PostDto postDto = PostDto.builder().description("Test desc")
                .title("Test 1").build();
        BDDMockito.when(postService.listPosts()).thenReturn(Flux.just(postDto));
        Flux<PostDto> responseBody = webTestClient.get().uri("/posts")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(PostDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(postDto)
                .verifyComplete();

    }
}
