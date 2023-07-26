package com.example.reactive.functional;

import com.example.reactive.functional.dto.PostDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReactiveRestEtoETest {


    @Autowired
    private WebTestClient webTestClient;



    @Test
    void createTest(){
        PostDto postDto = PostDto.builder().description("Test desc")
                .title("Test 1").build();

     webTestClient.post().uri("/posts")
                .body(Mono.just(postDto), PostDto.class)
                .exchange()
                .expectStatus()
                .isOk();
    }



}
