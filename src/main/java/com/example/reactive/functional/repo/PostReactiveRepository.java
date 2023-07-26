package com.example.reactive.functional.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PostReactiveRepository extends ReactiveCrudRepository<Post, Long> {

}
