package com.example.reactive.functional.repo;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class Post {

    @Id
    private long ID;

    @NonNull
    private String title;

    @NonNull
    private String description;

    private LocalDateTime created_at;
}
