package com.example.reactive.functional.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class PostDto {

    private long id;

    private String title;

    private String description;

    private LocalDateTime created;
}
