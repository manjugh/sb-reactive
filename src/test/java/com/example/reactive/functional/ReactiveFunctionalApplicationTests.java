package com.example.reactive.functional;

import com.example.reactive.functional.config.RouteConfig;
import com.example.reactive.functional.handler.EchoHandler;
import com.example.reactive.functional.handler.PostHandler;
import com.example.reactive.functional.dto.PostDto;
import com.example.reactive.functional.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
//@SpringBootTest
@AutoConfigureWebFlux

@WebFluxTest({PostHandler.class,EchoHandler.class})
@Import(RouteConfig.class)
class ReactiveFunctionalApplicationTests {

	@Autowired
	WebTestClient webTestClient;

	@MockBean
	PostService postService;



	@Test
	void contextLoads() {
	}


	@Test
	void createblogTest(){
		PostDto postDto = PostDto.builder().title("test").description("unit test").build();
		webTestClient.post().uri("/blog").contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(postDto),PostDto.class)
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	void echoTest(){
		//MockServerRequest body = MockServerRequest.builder().queryParam("name","Manju").build();
		Flux<String> responseBody = webTestClient.get().uri("/echo?name=manju")
				.exchange()
				.expectStatus()
				.isOk()
				.returnResult(String.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNextMatches(s -> s.equals("\"manju\""))
				.verifyComplete();
	}
}
