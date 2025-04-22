package com.example.simpleBoard.Domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "posts_seq")
    @SequenceGenerator(name = "posts_seq", sequenceName = "POSTS_SEQ", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title; // 제목
    @Column(nullable = false, length = 20)
    private String author; // 작성자

    @Lob // CLOB 또는 BLOB 타입으로 매핑합니다. (긴 텍스트 내용을 위해)
    @Column(nullable = false) // 'content' 컬럼, null 허용 안함
    private String content; // 내용

    @Column(length = 20) // 'password' 컬럼, 길이 20 (선택적 필드이므로 nullable = true 기본값 사용)
    private String password;

    public Post(String title, String author, String content, String password) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.password = password;
    }

    @CreationTimestamp // 엔티티가 처음 저장될 때 현재 시간을 자동으로 저장
    @Column(name = "created_at", nullable = false, updatable = false) //업데이트 불가
    private LocalDateTime createdAt;

}
