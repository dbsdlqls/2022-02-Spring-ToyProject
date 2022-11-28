package com.jojoldu.book.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyPageController {

    @GetMapping("")
    public String myPageHome(/*Model model, @AuthenticationPrincipal Member currentMember*/ ) {
//        List<Category> categoryList = categoryService.findAll();
//
//        MemberForm memberForm = new MemberForm();
//        memberForm.setName(currentMember.getUsername());
//        memberForm.setEmail(currentMember.getEmail());
//
//        model.addAttribute("categoryList", categoryList);
//        model.addAttribute("memberForm", memberForm);
        return "mypage";
    }

    /**
     * 회원정보 수정하기
     */
    @GetMapping("/mypage/edit")
    public String mypageEditForm(){
        return "mypage/edit";
    }

    @PostMapping("/mypage/edit")
    public String mypageEdit(){
        return "mypage/edit";
    }

    /**
     * 내가 쓴 게시글 보기
     */
    @GetMapping("/mypage/posts")
    public String viewPosts() {
        return "mypage/posts";
    }

    /**
     * 내가 좋아요한 글
     */
    @GetMapping("/mypage/likes")
    public String viewLikes() {
        return "mypage/like";
    }
    /**
     *  내가 쓴 댓글 보기
     */
    @GetMapping("/mypage/comments")
    public String viewComments() {
        return "mypage/comments";
    }
    /**
     * 탈퇴하기 기능
     * 다시 홈으로 들어감
     */
    @GetMapping("/mypage/delete")
    public String mypageDelete() {
        return "/";
    }

}
