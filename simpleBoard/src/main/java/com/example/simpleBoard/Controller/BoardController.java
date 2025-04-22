package com.example.simpleBoard.Controller;

import com.example.simpleBoard.DTO.PostCreateRequestDto;
import com.example.simpleBoard.DTO.PostDetailResponseDto;
import com.example.simpleBoard.DTO.PostListResponseDto;
import com.example.simpleBoard.DTO.PostUpdateRequestDto;
import com.example.simpleBoard.Service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @GetMapping
    public String listPosts(Model model) {
        List<PostListResponseDto> posts = boardService.findAllPosts();
        model.addAttribute("posts", posts);
        return "posts/list";
    }

    @GetMapping("/{id}")
    public String detailPost(@PathVariable("id") Long id, Model model){
        PostDetailResponseDto post = boardService.findPostById(id);
        model.addAttribute("post", post);
        return "posts/detail";
    }

    @GetMapping("/new")
    public String newPostForm(Model model) {
        model.addAttribute("postForm", new PostCreateRequestDto());
        return "posts/form";
    }

    @PostMapping
    public String savePost(PostCreateRequestDto postForm, RedirectAttributes redirectAttributes) {
        Long savedPostId = boardService.savePost(postForm);
        redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 등록되었습니다.");
        return "redirect:/posts"; // 목록 페이지로 리다이렉트
    }

    @GetMapping("/{id}/edit")
    public String editPostForm(@PathVariable("id") Long id, Model model) {
        PostDetailResponseDto postDto = boardService.findPostById(id);
            model.addAttribute("editForm", postDto);
        model.addAttribute("postId", id);
                return "posts/editForm";
    }

    @PostMapping("/{id}")
    public String updatePost(@PathVariable("id") Long id,
                             PostUpdateRequestDto updateForm, // 폼 데이터를 받을 DTO (password 필드 포함)
                             RedirectAttributes redirectAttributes) {
        try {
            boardService.updatePost(id, updateForm);
            redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 수정되었습니다.");
            return "redirect:/posts/" + id;

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/posts/" + id + "/edit";
        }

    }
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable("id") Long id,
                             @RequestParam("password") String password,
                             RedirectAttributes redirectAttributes) {
        System.out.println("이곳");
        try {
            boardService.deletePost(id, password);
            redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 삭제되었습니다.");
            return "redirect:/posts";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/posts/" + id;
        }
    }
}
