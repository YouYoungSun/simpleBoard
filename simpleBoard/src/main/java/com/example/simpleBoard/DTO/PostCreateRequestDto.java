package com.example.simpleBoard.DTO;

import com.example.simpleBoard.Domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostCreateRequestDto {
    private String title;
    private String author;
    private String content;
    private String password;

    //DTO를 Entity로 변환
    public Post toEntity(){
        return new Post(title, author, content, password);
    }
}
