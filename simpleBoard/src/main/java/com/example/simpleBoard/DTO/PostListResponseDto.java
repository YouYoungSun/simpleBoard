package com.example.simpleBoard.DTO;

import com.example.simpleBoard.Domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
public class PostListResponseDto {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime createdAt;


    //엔티티를 DTO로 변환
    public PostListResponseDto(Post entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.createdAt = entity.getCreatedAt();
    }
}
