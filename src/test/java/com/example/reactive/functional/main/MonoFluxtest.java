package com.example.reactive.functional.main;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.subscriber.TestSubscriber;

public class MonoFluxtest {

    @Test
    public void testMono() {
        Mono.just("test mono").map(String::toUpperCase)
                .log()
                //.subscribe(System.out::println, e -> System.out.println(e.getMessage()));
                .subscribe(new TestSubscriber());


    }

    @Test
    public void testFlux() {
        Flux.just("spring", "boot", "k8s", "microservices")
                .log()
                .concatWithValues("docker")
                .concatWith(Mono.just("cloud"))
                .subscribe(new TestSubscriber());


    }

    class TestSubscriber implements CoreSubscriber {
        Subscription subscription;

        @Override
        public void onSubscribe(Subscription subscription) {
            subscription = this.subscription;
            subscription.request(1);
        }

        @Override
        public void onNext(Object o) {
            System.out.println("on next");
            System.out.println("Printing from test subscriber " + o.toString());
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onComplete() {

        }
    }
}
