package com.example.simpleBoard.Service;

import com.example.simpleBoard.DTO.PostCreateRequestDto;
import com.example.simpleBoard.DTO.PostDetailResponseDto;
import com.example.simpleBoard.DTO.PostListResponseDto;
import com.example.simpleBoard.DTO.PostUpdateRequestDto;
import com.example.simpleBoard.Domain.Post;
import com.example.simpleBoard.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }


    //게시글 전체목록
    public List<PostListResponseDto> findAllPosts(){
        List<Post> posts = boardRepository.findAll();
        return posts.stream().map(PostListResponseDto::new).collect(Collectors.toList());
    }

    //특정id 게시물 조회
    public PostDetailResponseDto findPostById(Long id){
        Post post = boardRepository.findById(id).orElseThrow(()
                -> new IllegalAccessError("해당 Id의 게시글이 없습니다. id=" + id));
        return new PostDetailResponseDto(post);
    }

    //새 게시물 저장
    @Transactional//데이터를 변경하는 작업이므로 클래스 레벨의 readOnly 설정을 덮어쓰고 쓰기 가능 트랜잭션 적용
    public Long savePost(PostCreateRequestDto requestDto){
        Post post = requestDto.toEntity();
        Post savedPost = boardRepository.save(post);
        return savedPost.getId();
    }

    //게시글 수정
    @Transactional
    public Long updatePost(Long id, PostUpdateRequestDto requestDto){
        Post post = boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 Id의 게시글이 없습니다. id=" +  id));

        //비밀번호 확인 로직
        if (post.getPassword() != null && !post.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());

        return id;
    }

    @Transactional
    public void deletePost(Long id,String password){
        Post post = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글이 없습니다. id=" + id));

        if (post.getPassword() != null && !post.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        boardRepository.deleteById(id); // 해당 ID의 게시글 삭제

    }



}
